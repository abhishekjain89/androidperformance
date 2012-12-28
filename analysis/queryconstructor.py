class QueryConstructor():
	
	def __init__(self):
		self.selects = []
		self.tables = ["measurement"]
		self.wheres = []
		self.orderby = None
		self.groupby = None
		self.id = "measurementid"
	
	
	def selectToString(self):
		s=""
		
		for select in self.selects:
			s+=select+","
					
		return s[0:len(s)-1]
		
	def whereToString(self):
		s=""
		
		for where in self.wheres:
			s+=where+" and "
		
		return s[0:len(s)-5]
		
	def fromToString(self):
		s=""
		
		for table in self.tables:
			s+=table+","
		
		return s[0:len(s)-1]
	
	def toString(self):
		s  = "select " + self.selectToString() + " from " + self.fromToString() + " where " + self.whereToString() 		
		if(self.groupby!=None):
			s+=" group by " + self.groupby
		if(self.orderby!=None):
			s+=" order by " + self.orderby
			
		s+=";"
		return s
		
	def addSelect(self, table, field):
		
		if str(field) not in self.selects:
			self.selects.append(str(field))		
			self.safeAddTable(table)

	def addSelectHour(self, table, field):
		
		if str(field) not in self.selects:
			self.selects.append("date_part('hour',"+str(field)+")")		
			self.safeAddTable(table)

	def addSelectTimeOfDay(self, table, field):
		
		if str(field) not in self.selects:
			self.selects.append("date_part('hour',"+str(field)+")*60"+"date_part('minute',"+str(field)+")")		
			self.safeAddTable(table)
			
	def addSelectDayOfWeek(self, table, field):
		
		if str(field) not in self.selects:
			self.selects.append("extract(dow from "+str(field)+"::timestamp)")		
			self.safeAddTable(table)
			
	def addSelectMedian(self, table, field):
		
		if str(field) not in self.selects:
			self.selects.append("median(cast("+table+"."+str(field)+" as int))")		
			self.safeAddTable(table)
			
	def addSelectPercentile(self, table, field, percentile):
		
		if str(field) not in self.selects:
			self.selects.append("percentile"+str(percentile)+"(cast("+table+"."+str(field)+" as int))")		
			self.safeAddTable(table)
			
	def addSelectField(self, field):
		
		if str(field) not in self.selects:
			self.selects.append(str(field))		
		
						
	def addWhereEquals(self,table,field,value):		
		self.wheres.append(table+"."+field+"="+str(value))		
		self.safeAddTable(table)
		
	def addWhereNotEquals(self,table,field,value):		
		self.wheres.append(table+"."+field+"!="+str(value))		
		self.safeAddTable(table)
	
	def addWhereRaw(self,clause):		
		self.wheres.append(clause)				
		
	def addWhereGreaterThan(self,table,field,value):		
		self.wheres.append(table+"."+field+">"+str(value))		
		self.safeAddTable(table)
	
	def addWhereGreaterThanField(self,field,value):		
		self.wheres.append(field+">"+str(value))
		
	def addWhereDayOfWeekEquals(self, table, field, value):
		
		if str(field) not in self.selects:
			self.wheres.append("extract(dow from "+str(field)+"::timestamp)="+str(value))		
			self.safeAddTable(table)				
			
	def addWhereDayOfWeekLessThan(self, table, field, value):
		
		if str(field) not in self.selects:
			self.wheres.append("extract(dow from "+str(field)+"::timestamp)<"+str(value))		
			self.safeAddTable(table)				
			
	def addWhereDayOfWeekMoreThan(self, table, field, value):
		
		if str(field) not in self.selects:
			self.wheres.append("extract(dow from "+str(field)+"::timestamp)>"+str(value))		
			self.safeAddTable(table)				
	
		
	def addWhereLessThan(self,table,field,value):		
		self.wheres.append(table+"."+field+"<"+str(value))		
		self.safeAddTable(table)
	
	def addWhereEqualsString(self,table,field,value):
		self.addWhereEquals(table, field, "'"+value+"'")
		
	def addWhereNotEqualsString(self,table,field,value):
		self.addWhereNotEquals(table, field, "'"+value+"'")			
					
	def safeAddTable(self,table):
		if table not in self.tables:
			self.tables.append(table)
			if table == 'device':
				self.addWhereEquals("measurement","deviceid",table+".deviceid")
			else:
				self.addWhereEquals("measurement","measurementid",table+".measurementid")
	
	def setOrderBy(self,table,field):
		self.orderby = table+"."+field
		self.safeAddTable(table)
		
	def setOrderBy(self,field):
		self.orderby = field
		
	def setGroupBy(self,field):
		self.groupby = field
	
	def setGroupOrderSelectBy(self,table,field):
		self.setGroupBy(field)
		self.setOrderBy(field)
		self.addSelect(table,field)
		
	def applyMobileClauses(self):
		self.addWhereNotEqualsString("network","connectiontype","Wifi")
		self.addWhereNotEqualsString("network","connectiontype","")
			
	def applyLatencyClauses(self,dstip):
		tablename=None
		if "lastmile" in self.tables:
			tablename = "lastmile"
		elif "ping" in self.tables:
			tablename = "ping"
		
		if tablename==None:
			return
		
		self.addWhereGreaterThan(tablename,"avg",0)
		self.addWhereEqualsString(tablename,"dstip",dstip)
		self.addWhereLessThan(tablename,"avg",10000)
		
	def applyWarmupClauses(self):		
		self.addWhereLessThan("warmup_experiment","highest",30000)
		
	def applyWarmupSequenceClauses(self):		
		self.applyWarmupClauses()
		self.addWhereGreaterThan("warmup_ping","value",0)
		

#===============================================================================
# constructor = QueryConstructor()
# constructor.addSelect("lastmile","avg(\"avg\")")
# constructor.applyMobileClauses()
# constructor.applyLastmileClauses("www.google.com", 0, 2000)
# constructor.setGroupOrderSelectBy("battery", "10*cast((level/10) as int)")
# print constructor.toString()
#===============================================================================
