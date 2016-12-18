from Tkinter import *

master = Tk()

w = Canvas(master, width=200, height=300)
w.config(scrollregion=(0,0,200,300))

sbar=Scrollbar(master)
sbar.config(command=w.yview)
w.config(yscrollcommand=sbar.set)
sbar.pack(side=RIGHT, fill=Y)

w.pack(side=TOP, expand=YES, fill=BOTH)
e1=Entry(master=None)
e1.text="entry1"
e2=Entry(master=None)
e2.text="entry2"

w.create_window(30, 20, anchor=W, window=e1)
w.create_window(30, 60, anchor=W, window=e2)

mainloop()
