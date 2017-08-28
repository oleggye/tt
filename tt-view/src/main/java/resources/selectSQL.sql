SELECT record.id_record, record.date_from, record.date_to, cancellation.id_record
	FROM timetable.cancellation
right join timetable.record using(id_record)
where record.id_group = 1 
		and	not((record.date_to < '2017-06-12') or (record.date_from > '2017-06-15'))
		and (cancellation.id_record is null or 
									((cancellation.date_to <record.date_from) or (cancellation.date_from > record.date_to)));