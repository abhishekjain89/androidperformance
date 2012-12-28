#!/usr/bin/python
import psycopg2
import sys
import pprint

def query(text):
	#start the script

	conn_string = "host='localhost' dbname='androidperformance' user='postgres' password='pass'"
	# print the connection string we will use to connect
	#print "Connecting to database\n	->%s" % (conn_string)
	try:
		# get a connection, if a connect cannot be made an exception will be raised here
		conn = psycopg2.connect(conn_string)

		# conn.cursor will return a cursor object, you can use this cursor to perform queries
		cursor = conn.cursor()
		print text
		# execute our Query
		cursor.execute(text)

		# retrieve the records from the database
		records = cursor.fetchall()

		# print out the records using pretty print
		# note that the NAMES of the columns are not shown, instead just indexes.
		# for most people this isn't very useful so we'll show you how to return
		# columns as a dictionary (hash) in the next example.
		#pprint.pprint(records)
		
		return records
	except:
		# Get the most recent exception
		print  sys.exc_info()
		# Exit the script and print an error telling what happened.
		#sys.exit("Database connection failed!\n ->%s" % (exceptionValue))
		
		return ''


if __name__ == "__main__":
	sys.exit(main())
