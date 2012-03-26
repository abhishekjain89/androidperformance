import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.throughput_over_time())


data = tuple_gen.group_by(result,0)

plt.figure(1)                # the first figure

axisNum = len(data)*100 + 10

for key in data.keys():
	
	data2 = tuple_gen.group_by(data[key],1)
	axisNum += 1
	
	ax = plt.subplot(axisNum)

	plt.title(key)
	x_ticks_up = tuple_gen.hour_minute_period(data2["9912"],2,3,10)
	y_vals_up = (tuple_gen.base_int(data2["9912"],4))
	
	x_ticks_down = tuple_gen.hour_minute_period(data2["9710"],2,3,10)
	y_vals_down = (tuple_gen.base_int(data2["9710"],4))
	plt.ylabel('bps')
	plt.plot(x_ticks_up,y_vals_up,label='Upload')
	plt.plot(x_ticks_down,y_vals_down,label='Download')
	
plt.legend()
plt.show()



