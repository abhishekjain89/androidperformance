import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.ping_over_mobile_signal())


data = tuple_gen.group_by(result,0)

#plt.figure(1)                # the first figure

axisNum = 0
row = 0
col = 0
for key in data.keys():
	
	data2 = tuple_gen.group_by(data[key],1)
	list_keys = data2.keys()
	row+=1
	col = 0
	for key2 in sorted(list_keys):
		axisNum += 1
		col+=1
		ax = plt.subplot(len(data)+1, len(data2), axisNum)

		if row ==1 :
			plt.title(key2)
		if col ==1 :
			plt.ylabel(key)
					
		x_ticks = tuple_gen.base_int(data2[key2],2)
		y_vals = tuple_gen.normalize(tuple_gen.base_int(data2[key2],3))
		y_vals_2 = tuple_gen.normalize(tuple_gen.base_int(data2[key2],4))
		plt.scatter(x_ticks,y_vals)		
		#plt.plot(x_ticks,y_vals,label="avg")
		#plt.plot(x_ticks,y_vals_2,label="stddev")
		
		for tick in ax.get_xticklabels():
			tick.set_visible(False)
		for tick in ax.get_yticklabels():
			tick.set_visible(False)


plt.legend()
plt.show()



