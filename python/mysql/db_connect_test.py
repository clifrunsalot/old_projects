# cat employee.py 
import mysql.connector as db
from mysql.connector import errorcode


def insert_records(connection):
	'''
	Inserts three records into the Employee table.
	'''
	success = False
	cursor = connection.cursor()

	try:
		sql = """INSERT INTO EMPLOYEE (ID, FIRST, LAST, AGE) VALUES (1,'CLIF','HUDSON',51)""" 
		cursor.execute(sql)
		connection.commit()
		sql = """INSERT INTO EMPLOYEE (ID, FIRST, LAST, AGE) VALUES (2,'JOANN','HUDSON',50)""" 
		cursor.execute(sql)
		connection.commit()
		sql = """INSERT INTO EMPLOYEE (ID, FIRST, LAST, AGE) VALUES (3,'SIRENA','HUDSON',23)""" 
		cursor.execute(sql)
		connection.commit()

		cursor.execute("SELECT * FROM EMPLOYEE")		
		if len(cursor.fetchall()) == 3:
			success = True

	except db.Error as err:
		connection.rollback()	
		print(err)

	return success 


def read_table(connection):
	'''
	Return list of contents in Employee table.
	'''
	cursor = connection.cursor()
	query = ("SELECT * FROM EMPLOYEE")
	cursor.execute(query)

	empl_list = []
	rows = cursor.fetchall() 
	for r in rows:
		print("{:3d} {:10s} {:10s} {:5d}".format(r[0],r[1],r[2],r[3]))	


def create_table(connection):
	'''
	Create the table.
	'''
	success = False
	try:
		sql = """CREATE TABLE EMPLOYEE (
						 ID INT NOT NULL,
						 FIRST VARCHAR(20) NOT NULL,
						 LAST  VARCHAR(20) NOT NULL,
						 AGE INT NOT NULL)"""  

		cursor = connection.cursor()
		cursor.execute(sql)
		success = True

	except db.Error as err:
		if err.errno == errorcode.ER_TABLE_EXISTS_ERROR:
			cursor.execute("SELECT * FROM EMPLOYEE")		
			if len(cursor.fetchall()) == 0:
				success = True
		else:
			print(err)

	return success 

	
def delete_table(connection):
	'''
	Delete the Employee table if it exists.
	'''
	success = False
	try:

		# Let's drop the table and then create a new one.
		query = ("DROP TABLE IF EXISTS EMPLOYEE") 
		cursor = connection.cursor()
		cursor.execute(query)
		success = True
		
	except db.Error as err:
		# This means we tried to delete a non-existent table,
		# which is ok.
		if err.errno == errorcode.ER_BAD_TABLE_ERROR:
			success = True

	return success

	
def connect():
	'''
	Set up DB connection.
	'''

	try:

		config = {
			'user': 'clif',
			'password': '********',
			'host': '127.0.0.1',
			'database': 'bedrock',
			'raise_on_warnings': True,
		}

		conn = db.connect(**config)

	except db.Error as err:
		if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
			print("Something is wrong with your user name or password")
		elif err.errno == errorcode.ER_BAD_DB_ERROR:
			print("Database does not exist")
		else:
			print(err)

	return conn 


def close(connection):
	'''
	Close DB connection.
	'''
	if not connection is None:
		connection.close()



if __name__ == "__main__":
	
	conn = connect()
	if not conn is None:
		print("Connection established")
		deleted = delete_table(conn)
		print("Table deleted")
		if deleted:
			created = create_table(conn)
			if created:
				print("Created table")
				inserted = insert_records(conn)
				if inserted:
					print("Records inserted")
					read_table(conn)
					close(conn)
				else:
					print("Failed to insert records")
			else:
				print("Failed to create table")
		else:
			print("Failed to delete table")

