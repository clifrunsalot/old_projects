BASE = /Home/cbhuds/projects/cpp/make_example
EXE = hello
cc = g++
headers := $(wildcard $(BASE)/headers/*.h)
source := $(wildcard $(BASE)/src/*.cpp)

all: help

compile : $(source) $(headers)
	$(cc) -Wall -g -c $(source)

link : $(BASE)/*.o 
	$(cc) $(BASE)/*.o -o $(EXE) 

# Non-compilation oriented functions
.PHONY: all help clean show_files 

help:
	@echo
	@echo "usage: make [compile|link|show_files|clean]"
	@echo

show_files:
	@echo $(source)
	@echo $(headers)
	@echo $(objects)

clean:
	rm hello $(objects)
	@echo "done"


