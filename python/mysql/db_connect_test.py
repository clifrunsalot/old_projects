# cat employee.py 
import mysql.connector

conn = mysql.connector.connect(
         user='clif',
         password='**************',
         host='127.0.0.1',
         database='bedrock')

cur = conn.cursor()

query = ("SELECT * FROM Employee")

cur.execute(query)

for (id, age, first, last) in cur:
  print("{}, {}, {}, {}".format(id, age, first, last))

cur.close()
conn.close()

