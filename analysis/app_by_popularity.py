import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.app_by_popularity())

pprint.pprint(result)

N = len(result)
x_ticks = tuple_gen.base_str(result,2)
data_vals = tuple_gen.base_int(result,0)
width=0.35

ind = np.arange(N)  # the x locations for the groups


fig = plt.figure()
ax = fig.add_subplot(111)

rects1 = ax.bar(ind, data_vals, width, color='r')

# add some
ax.set_ylabel('Count')
ax.set_title('Total Data by Application')
ax.set_xticks(ind+width)
ax.set_xticklabels(x_ticks,rotation='vertical')

ax.legend( (rects1[0],), ('Application',) )

plt.show()





