*** Program to read text files and update tables ***

import MySQLdb

cnx = MySQLdb.connect(host="xxx.x.x.x",
                      user="xxxx",
                      passwd="********",
                      db="xxxx")
cursor = cnx.cursor()

with open('C:/Users/Apoorv Mishra/Downloads/trips/trips/calendar.txt', 'r') as f:
    next(f)
    for line in f:

        values = [line.split(",") for line in f]
        query = "INSERT INTO " \
                "calendar(service_id, monday, tuesday, wednesday, thursday, friday, saturday, sunday," \
                " start_date, end_date) " \
                "VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.executemany(query, values)
f.close()

with open('C:/Users/Apoorv Mishra/Downloads/trips/trips/routes.txt', 'r') as f:
    next(f)
    for line in f:

        values = [line.split(",") for line in f]
        query = "INSERT INTO " \
                "routes(route_id, route_short_name, route_long_name, route_type, route_url)" \
                "VALUES (%s,%s,%s,%s,%s)"
        cursor.executemany(query, values)
f.close()

with open('C:/Users/Apoorv Mishra/Downloads/trips/trips/stops.txt', 'r') as f:
    next(f)
    for line in f:

        values = [line.split(",") for line in f]
        query = "INSERT INTO stops(stop_id, stop_lat, stop_lon, stop_name) VALUES (%s,%s,%s,%s)"
        cursor.executemany(query, values)
f.close()

with open('C:/Users/Apoorv Mishra/Downloads/trips/trips/trips.txt', 'r') as f:
    next(f)
    for line in f:

        values = [line.split(",") for line in f]
        query = "INSERT INTO " \
                "trips(route_id, trip_id, service_id, direction_id, block_id)"\
                "VALUES (%s,%s,%s,%s,%s)"
        cursor.executemany(query, values)
f.close()

with open('C:/Users/Apoorv Mishra/Downloads/trips/trips/stop_times.txt', 'r') as f:
    next(f)
    for line in f:

        values = [line.split(",") for line in f]
        query = "INSERT INTO " \
                "stop_times(trip_id, arrival_time, departure_time, stop_id, stop_sequence)"\
                "VALUES (%s,%s,%s,%s,%s)"
        cursor.executemany(query, values)
f.close()

query = "SELECT DISTINCT calendar.service_id, trips.trip_id, monday, tuesday, wednesday, thursday, friday, saturday, " \
        "sunday from test.calendar join trips on calendar.service_id = trips.service_id  join stop_times" \
        " on stop_times.trip_id = trips.trip_id where cast(stop_times.arrival_time as time) > '24:00:00'"

cursor.execute(query)

myArray = cursor.fetchall()
myList = list()
for row in enumerate(myArray):
    
    print(row)
    i=0
    myList.insert(0, int(myArray[i][8]))
    myList.insert(1, int(myArray[i][2]))
    myList.insert(2, int(myArray[i][3]))
    myList.insert(3, int(myArray[i][4]))
    myList.insert(4, int(myArray[i][5]))
    myList.insert(5, int(myArray[i][6]))
    myList.insert(6, int(myArray[i][7]))

    cursor.execute("UPDATE calendar SET monday=%s, tuesday=%s, wednesday=%s, thursday=%s, friday=%s, saturday=%s,"
                   "sunday=%s WHERE calendar.service_id=%s" % (myList[0], myList[1], myList[2], myList[3],
                                                               myList[4], myList[5], myList[6], myArray[i][0]))
    i=i+1
cursor.execute("UPDATE stop_times SET arrival_time = TIMEDIFF(arrival_time,'24:00:00'), "
               "departure_time = TIMEDIFF(departure_time,'24:00:00') "
               "WHERE cast(stop_times.arrival_time as time) > '24:00:00'")


query = "Insert into table1(stop_id, monday, route_id, arrival_time) Select stop_times.stop_id, calendar.monday, " \
        "routes.route_id, stop_times.arrival_time from stop_times join stops on stops.stop_id = stop_times.stop_id " \
        "join trips on trips.trip_id=stop_times.trip_id join routes on routes.route_id=trips.route_id " \
        "join calendar on trips.service_id=calendar.service_id where calendar.monday=1;"
cursor.execute(query)

query = "Insert into table_2(hours_of_service, stop_id) select timediff(max(arrival_time), min(arrival_time)) " \
        "as hours_of_service,  stop_id from table1 group by stop_id;"
cursor.execute(query)

query = "Insert into final_new(stop_id, monday, route_id, hours_of_service) " \
        "select distinct table1.stop_id, table1.monday, table1.route_id, table_2.hours_of_service " \
        "from table1, table_2 where table1.stop_id=table_2.stop_id;"
cursor.execute(query)

cnx.commit()
cnx.close()

