package net.nosegrind.apiframework

class Stat {

	static belongsTo = [Person]

	Person user
	Integer code
	String uri
	Short hour
	Short day
	Short month
	Long year
	Short wkOfYr
	Long timestamp

	static constraints = {
		user nullable:false
		code nullable:false
		uri nullable:false
		hour nullable:false
		day nullable:false
		month nullable:false
		year nullable:false
		wkOfYr nullable:false
		timestamp nullable:false
	}

	static mapping = {
		hour index: 'stat_hour_idx'
		day index: 'stat_day_idx'
		month index: 'stat_month_idx'
		year index: 'stat_year_idx'
		wkOfYr index: 'stat_wkOfYr_idx'
		timestamp sqlType: "bigint"
	}
}
