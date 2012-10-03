import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt
import matplotlib

matplotlib.use('Agg')
result = database.query(query.usage_over_hour_of_day())

pprint.pprint(result)

N = len(result)
x_ticks = tuple_gen.hour(result,0)
wifi_vals = tuple_gen.MB(result,1)
mobile_vals = tuple_gen.MB(result,2)

ind = np.arange(N)  # the x locations for the groups
width = 0.35       # the width of the bars

fig = plt.figure()
ax = fig.add_subplot(111)
rects1 = ax.bar(ind, wifi_vals, width, color='r')

rects2 = ax.bar(ind+width, mobile_vals, width, color='y')

# add some
ax.set_ylabel('MB')
ax.set_title('Usage over Hour of Day')
ax.set_xticks(ind+width)
ax.set_xticklabels(x_ticks)

ax.legend( (rects1[0], rects2[0]), ('Wi-fi', 'Mobile') )

plt.show()





