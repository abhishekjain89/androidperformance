import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.signalstrength_count())

data = tuple_gen.group_by(result,0)

plt.figure(1)                # the first figure

subplot_count = 811

for key in data.keys():
	plt.subplot(subplot_count)
	#plt.ylim(0,31)
	plt.title(key)
		
	x_ticks = (tuple_gen.base_int(data[key],1))
	y_vals = (tuple_gen.base_int(data[key],2))
	
	N = len(x_ticks)
	
	ind = np.arange(N)  # the x locations for the groups
	width = 0.35       # the width of the bars
	
	rects1 = plt.bar(ind, y_vals, width, color='r')
	
	plt.ylabel('count')
	
	plt.xticks(x_ticks)
	#plt.xticklabels(x_ticks)
	subplot_count+=1
	if subplot_count>818:
		break
	
plt.show()



