import numpy as np
import matplotlib.pyplot as plt
import database,query,tuple_gen
import pprint
import numpy as np
import matplotlib.pyplot as plt


result = database.query(query.signalstrength_scatter_over_day())

data = tuple_gen.group_by(result,0)

plt.figure(1)


for key in data.keys():
    #plt.subplot(3,2,axisNum)
    #plt.ylim(0,31)
    plt.title(key)
    
    x_ticks = tuple_gen.hour_minute_in_mins(data[key],1,2)
    y_vals = (tuple_gen.base_float(data[key],3))
    
    heatmap, xedges, yedges = np.histogram2d(y_vals, x_ticks, bins=(100,100))
 
    extent = [90, 100, 0, 100]
    #plt.clf()
    #fig.add_axes([0, 1, 0.5, 0.5])
    plt.imshow(heatmap,extent=extent)
    plt.colorbar()
    plt.show()
        
    #plt.plot(x_ticks,y_vals)
    #plt.scatter(x_ticks,y_vals)
   
plt.show()



