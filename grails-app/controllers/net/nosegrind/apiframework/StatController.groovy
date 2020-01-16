package net.nosegrind.apiframework

import java.sql.Timestamp
import java.text.SimpleDateFormat
import org.grails.core.DefaultGrailsDomainClass
import org.grails.core.artefact.DomainClassArtefactHandler

class StatController {
	
	def springSecurityService
	def statsService

	HashMap show() {
		Integer time = System.currentTimeMillis()
		Integer day = (Integer) time/((1000*60*60*24)+1)
		switch(params.type){
			case '2':
				// 12 months
				def year = getStatsByYear()
				return [stat:year]
				break
			case '1':
				// data for entire month
				def month = getStatsByMonth()
				return [stat:month]
				break
			case '0':
			default:
				// data for all 24 hr periods
				def today = getStatsByDay()
				return [stat:today]
				break
		}
	}

	// First Day Of Week
	public long getFDOW() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
		c.set(Calendar.HOUR_OF_DAY, 0)
		c.set(Calendar.MINUTE, 0)
		c.set(Calendar.SECOND, 0)
		c.set(Calendar.MILLISECOND, 0)
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek())
		long fdow = c.getTimeInMillis() / 1000
		return fdow
	}

	// Last Day Of Week
	public long getLDOW() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
		c.set(Calendar.HOUR_OF_DAY, 0)
		c.set(Calendar.MINUTE, 0)
		c.set(Calendar.SECOND, 0)
		c.set(Calendar.MILLISECOND, 0)
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek())
		c.add(Calendar.DATE, +7)
		long ldow = c.getTimeInMillis() / 1000
		return ldow
	}

	ArrayList getStatsByDay(){
		Calendar cal = Calendar.getInstance();
		Short dy = cal.get(Calendar.DAY_OF_MONTH);
		Short mn = cal.get(Calendar.MONTH)
		Long yr = cal.get(Calendar.YEAR);

		ArrayList stats = []
		// init hours
		for(int i=0;i<=23;i++){
			stats[i] = ['time':"${i+1}",'count':'0','uri':"null",'username':"null"]
		}

		// get data
		ArrayList data = Stat.executeQuery("select S.hour,count(S.uri),S.uri,P.username from Stat S join S.user P where S.day=? and S.month=? and S.year=? group by S.hour order by S.hour ASC",[dy,mn,yr])
		data.each(){it ->
			stats[it[0]-1] = ['time':"${it[0]}",'count':"${it[1]}",'uri':"${it[2]}",'username':"${it[3]}"]
		}

		return stats
	}

	ArrayList getStatsByMonth(){
		ArrayList stats = []
		Calendar cal = Calendar.getInstance();
		Short ldom = cal.getActualMaximum(Calendar.DATE)
		Short mn = cal.get(Calendar.MONTH)
		Long yr = cal.get(Calendar.YEAR);

		// init hours
		for(int i=0;i<=(ldom-1);i++){
			stats[i] = ['time':"${i+1}",'count':'0','uri':"null",'username':"null"]
		}

		// get data
		ArrayList data = Stat.executeQuery("select S.day,count(S.uri),S.uri,P.username from Stat S join S.user P where S.day>=1 and S.day<=? and S.month=? and S.year=? group by S.day order by S.day ASC",[ldom,mn,yr])
		data.each(){it ->
			stats[it[0]-1] = ['time':"${it[0]}",'count':"${it[1]}",'uri':"${it[2]}",'username':"${it[3]}"]
		}
		return stats
	}

	LinkedHashMap formatDomainObject(Object data){
		LinkedHashMap newMap = [:]

		newMap.put('id', data?.id)
		newMap.put('version', data?.version)

		//DefaultGrailsDomainClass d = new DefaultGrailsDomainClass(data.class)

		DefaultGrailsDomainClass d = grailsApplication?.getArtefact(DomainClassArtefactHandler.TYPE, data.class.getName())

		if (d!=null) {
			// println("PP:"+d.persistentProperties)

				d?.persistentProperties?.each() { it2 ->
					if (it2?.name) {
						if (DomainClassArtefactHandler.isDomainClass(data[it2.name].getClass())) {
							newMap["${it2.name}Id"] = data[it2.name].id
						} else {
							newMap[it2.name] = data[it2.name]
						}
					}
				}

		}
		return newMap
	}
}
