import net.nosegrind.apiframework.StatsService
import net.nosegrind.apiframework.Stat
import grails.util.Environment


class StatsSchedJob {

    def statsService

    static triggers = {
		if (Environment.current != Environment.TEST) {
			cron name: 'myTrigger2', cronExpression: "0 */10 * ? * *"
		}
    }

    def execute() {
		def temp = statsService.getStatsCache()
		statsService.flushAllStatsCache()

		temp.each(){ it ->
			// int user = it[0]
			// int code = it[1]
			// String uri = it[2]
			// BigInteger timestamp = it[3]

			Calendar calendar = Calendar.getInstance()
			calendar.setTimeInMillis((Long)it[3])
			Short hr = calendar.get(Calendar.HOUR_OF_DAY)
			Short dy = calendar.get(Calendar.DAY_OF_MONTH)
			Short mn = calendar.get(Calendar.MONTH)+1
			Short wk = calendar.get(Calendar.WEEK_OF_YEAR)
			Long yr = calendar.get(Calendar.YEAR)

			Stat st = new Stat(user:it[0],code:it[1],uri:"${it[2]}",hour:hr,day:dy,month:mn,year:yr,wkOfYr:wk, timestamp:it[3])
			if(!st.save(flush:true,failOnError:true)){
				st.errors.allErrors.each { println(it2) }
			}
		}
    }

}
