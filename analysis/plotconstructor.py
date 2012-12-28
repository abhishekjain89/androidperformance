import numpy as np
import tuple_gen
import matplotlib.pyplot as plt

class Mapping():
	
	def __init__(self, data, xcolumn, ycolumn, name):
		self.data = data
		self.name = name
		
		self.xticks = tuple_gen.base_smart(self.data, xcolumn)
		self.yvals = tuple_gen.base_float(self.data, ycolumn)
		
	def transformToCDF(self):
		sum = 0
		
		new_tuple = ()
		for i in range(0, self.length()):
			sum += self.yvals[i]
			new_tuple += (sum,)
		
		self.yvals = ()
		
		for i in range(0, self.length()):
			self.yvals += ((new_tuple[i]) / sum,)
		
			
	def length(self):
		return len(self.data)
	
class MappingWithBounds(Mapping):
	
	def __init__(self, data, xcolumn, ycolumn, boundsLow, boundsHigh, name):
		Mapping.__init__(self, data, xcolumn, ycolumn, name)
		self.yerror = tuple_gen.getbounds(self.data, self.yvals, boundsLow, boundsHigh)



class BasePlot():
	def __init__(self):
		self.ylabel = ''
		self.xlabel = ''
		self.title = ''
		
	def show(self):
		plt.show()
			
	def setYLabel(self, label):
		self.ylabel = label
		
	def setXLabel(self, label):
		self.xlabel = label
		
	def setTitle(self, title):
		self.title = title
		
	def save(self, filename):
		plt.savefig("screenshots/" + filename + ".png", bbox_inches=0)

class SimpleBasePlot(BasePlot):
	def __init__(self):
		BasePlot.__init__(self)
		self.maps = []
		self.color = 'r'		
		self.legend = ''
		self.showLegend=False
		
	def addMapping(self, map):
		self.maps.append(map)
		
	def setColor(self, color):
		self.color = color
		
	def setLegend(self, legend):
		self.legend = legend
		self.showLegend=True
		
	def transformToCDF(self):
		for map in self.maps:
			map.transformToCDF()
		plt.grid(True)
		

class SimpleBarPlot(SimpleBasePlot):
	
	def __init__(self):
		SimpleBasePlot.__init__(self)
		#super(SimpleBarPlot, self).__init__(data)	
		self.ax = plt.figure().add_subplot(111)
		self.width = 0.35
				
	def construct(self):
		map = self.maps[0]
		ind = np.arange(map.length())+0.5
		if map.__class__.__name__ == 'MappingWithBounds':
			rects1 = self.ax.errorbar(ind, map.yvals, yerr=map.yerror, fmt='o')
		elif map.__class__.__name__ == 'Mapping':
			rects1 = self.ax.bar(ind, map.yvals, self.width, color=self.color)
		
		self.ax.set_ylabel(self.ylabel)
		self.ax.set_xlabel(self.xlabel)
		self.ax.set_title(self.title)
		self.ax.set_xticks(ind + self.width)
		self.ax.set_xticklabels(map.xticks, rotation='vertical')
		if self.showLegend:
			self.ax.legend((rects1[0],), (self.legend,))
		
class LinePlot(SimpleBasePlot):
	
	def __init__(self):
		SimpleBasePlot.__init__(self)
		plt.figure(1)
				
	def construct(self):	
		count = len(self.maps)
		plt.title(self.title)
		plt.ylabel(self.ylabel)
		plt.xlabel(self.xlabel)
		if count == 1:
			map = self.maps[0]
			plt.plot(map.xticks, map.yvals, "r")
			plt.legend((map.name), 'lower right')
		elif count == 2:
			map = self.maps[0]
			map2 = self.maps[1]
			plt.plot(map.xticks, map.yvals, "r", map2.xticks, map2.yvals, "b-")
			plt.legend((map.name, map2.name), 'lower right')
		elif count == 3:
			map = self.maps[0]
			map2 = self.maps[1]
			map3 = self.maps[2]
			plt.plot(map.xticks, map.yvals, "r", map2.xticks, map2.yvals, "b-", map3.xticks, map3.yvals, "g-")
			plt.legend((map.name, map2.name, map3.name), 'lower right')
		elif count == 4:
			map = self.maps[0]
			map2 = self.maps[1]
			map3 = self.maps[2]
			map4 = self.maps[3]			
			plt.plot(map.xticks, map.yvals, "r", map2.xticks, map2.yvals, "b-", map3.xticks, map3.yvals, "g-", map4.xticks, map4.yvals, "y-")
			plt.legend((map.name, map2.name, map3.name, map4.name), 'lower right')
			
class ScatterPlot(SimpleBasePlot):
	
	def __init__(self):
		SimpleBasePlot.__init__(self)
		plt.figure(1)
				
	def construct(self):	
		map = self.maps[0]
		plt.title(self.title)
		plt.ylabel(self.ylabel)
		plt.xlabel(self.xlabel)		
		plt.plot(map.xticks, map.yvals, ".")
		
				
			
