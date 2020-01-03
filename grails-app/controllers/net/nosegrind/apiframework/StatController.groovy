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
		switch(params.eventType){
			case 3:
				// 12 months
				def year = getStatsByYear()
				println("thisyear")
				return [stats:year]
				break
			case 2:
				// 4 weeks / 1 month
				def week = getStatsByMonth()
				println("thismonth")
				return [stats:month]
				break
			case 1:
				// 7 days / 1 week
				def week = getStatsByWeek()
				println("thisweek")
				return [stats:week]
				break
			case 0:
			default:
				// 1 day
				def today = getStatsByDay()
				LinkedHashMap codeTotals = [:]
				LinkedHashMap userTotals = [:]

				println(today)
				//today.each() { k,v ->

					//def thisStat = formatDomainObject(it)
/*
					Timestamp tstamp = new Timestamp(thisStat.timestamp)
					Date date = new Date(tstamp.getTime())
					SimpleDateFormat sdf = new SimpleDateFormat("kk")
					Integer hr = sdf.format(date).toInteger()

					String authority = springSecurityService.principal.authorities*.authority[0]

					if(apiTotals["${hrs[hr]}"]==null) {
						apiTotals["${hrs[hr]}"] = 1
					}else{
						apiTotals["${hrs[hr]}"] += 1
					}

					if(codeTotals["${thisStat.code}"]==null){
						codeTotals["${thisStat.code}"] = 1
					}else{
						codeTotals["${thisStat.code}"] += 1
					}

					Person user = Person.get(thisStat.user.toLong())
					if(userTotals["${user.username}"]==null){
						userTotals["${user.username}"] = 1
					}else{
						userTotals["${user.username}"] += 1
					}

 */
				//}

				return [stat:today]
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

	public long getYesterday() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
		c.set(Calendar.HOUR_OF_DAY, 0)
		c.set(Calendar.MINUTE, 0)
		c.set(Calendar.SECOND, 0)
		c.set(Calendar.MILLISECOND, 0)
		long yesterday = c.getTimeInMillis() / 1000
		return yesterday
	}
	LinkedHashMap getStatsByWeek(){
		long fdow = getFDOW()
		long ldow = getLDOW()
		List stats = Stat.findByTimestampBetween(fdow, ldow)
		return stats
	}

	ArrayList getStatsByDay(){
		ArrayList stats = []
		// init hours
		for(int i=0;i<=23;i++){
			stats[i] = ['time':"${i+1}",'count':'0']
		}

		// get data
		ArrayList data = Stat.executeQuery("select hour,count(id),hour from Stat group by hour order by hour ASC")
		data.each(){it ->
			stats[it[0]-1] = ['time':"${it[0]}",'count':"${it[1]}"]
		}
		println("post_init:"+stats)
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
