import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.signalstrength_over_hour_of_day())


data = tuple_gen.group_by(result,0)

plt.figure(1)                # the first figure

subplot_count = 511

for key in data.keys():
	plt.subplot(subplot_count)
	#plt.ylim(0,31)
	plt.title(key)
	x_ticks = tuple_gen.hour_minute_period(data[key],1,2,10)
	y_vals = (tuple_gen.base_float(data[key],3))

	plt.plot(x_ticks,y_vals)
	subplot_count+=1
	
plt.show()



