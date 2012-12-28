import numpy as np

class Column():
	
	def __init__(self,name,columnNum,type,isOutput):
		self.name = name
		self.columnNum = columnNum
		self.type = type
		self.isOutput = isOutput

class OrangeConstructor():
	
	def __init__(self):
		self.columns = []
		
	def add(self,column):
		self.columns.append(column)
		
			
	def length(self):
		return len(self.column)
	
	def saveToFile(self,result,filename):
		data_file = open(filename, "wb")
		for column in self.columns:
			data_file.write(column.name+"\t")
		data_file.write("\n")
		for column in self.columns:
			data_file.write(column.type+"\t")
		data_file.write("\n")
		for column in self.columns:
			if(column.isOutput):
				data_file.write("class\t")
			else:
				data_file.write("\t")
		data_file.write("\n")
		
		for row in result:
			for column in self.columns:
				data_file.write(str(row[column.columnNum])+"\t")
			data_file.write("\n")
		
		data_file.close()