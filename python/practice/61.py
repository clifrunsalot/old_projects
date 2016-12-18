# coding: utf-8

comments = '''

#lists
small_birds = ['canary','parrot','finch']
print('small_birds: ',small_birds)

extinct_birds = ['emu','ostrich','king penguin']
print('extinct_birds: ',extinct_birds)

small_birds.append(extinct_birds)
print('appended extinct_birds to small_birds: ',small_birds)

# in list
vowels = 'aeiou'
for v in vowels:
	if v == 'e' or v == 'x':
		print(v,' is a vowel')
	else:
		print('x is NOT a vowel')

# tuples
names_tuple = {'clif','joann','sirena','madison'}
for i in names_tuple:
	print(i)
names_list = ['clif','joann','sirena','madison']
for i in names_list:
	print(i)

# dictionaries
names_tuple = {'dad':'clif','mom':'joann','kid':'sirena','dog':'madison'}
names_dict = dict(names_tuple)
print('dictionary: ',names_dict)
print('dict keys:  ',names_dict.keys())
print('dict values:',names_dict.values())

# loops
count = 10
cnt = 0
while cnt < count:
	print(cnt)
	cnt += 1

name = input("Enter your name: ")
print(name)

# dictionary with nested tuples
drinks = {'screwdriver':{'oj','vodka'},'rum and coke':{'rum','coke'}}
for drink,ingredients in drinks.items():
	print(drink, " has the following ingredients: ", ', '.join(ingredients))

# standard input in loop
while True:
    stuff = input('Enter a string [q to exit]')
    if stuff == 'q':
        print("quiting")
        break
    print('stuff')
print('done')

# zip
days = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday']
fruits = ['banana','orange','peach']
drinks = ['coffee','tea','beer']
desserts = ['tiramisu','ice cream','pie','pudding']
for day,fruit,drink,dessert in zip(days,fruits,drinks,desserts):
    print('On ' + day + ', eat ' + fruit + ', drink ' + drink + ', enjoy ' + dessert)

print (list(zip(fruits,desserts)))
print (dict(zip(fruits,desserts)))

# ranges
for x in range(-4,4):
    print(x)

print(list(range(-4,4)))
print(list(range(-4,4,2)))

# list comprehension
number_list = [num for num in range(-5,5,1)]
print(number_list)

number_list = [num-1 for num in range(-5,5,1)]
print(number_list)

# list comprehension with condition
number_list = [num for num in range(-5,5,1) if num % 2]
print(number_list)

rows = range(1,4)
cols = range(1,3)
cells = [(row,col) for row in rows for col in cols]
print(cells)

# set comprehension
word = 'letters'
letter_counts = {letter: word.count(letter) for letter in word}
print(letter_counts)

# set comprehension efficient
word = 'letters'
letter_counts = {letter: word.count(letter) for letter in set(word)}
print(letter_counts)

#set comprehension find unique
data = '12 18 19 1 3 4 1 19'
data_list = [num for num in data.split(' ') if num != '']
print(data_list)

data_set = {num: data_list.count(num) for num in data_list}
print('unique: ',data_set)

data_sorted_set = [int(num) for num in data_set]
print(sorted(data_sorted_set))

# uniq data with counts
input = '1 2 3 4 2 5 0 -3 23 56 999 4 34'
input_list = input.split(' ')
num_cnt = {int(num): input_list.count(num) for num in input_list if input_list.count(num) > 1}
for itm in num_cnt.items():
    print(itm)
print('\n')

# extracting uniq values only
input = '99 -2 4 77 2 89 77 3 2 7 99 1234 1234 3 5 88 4'
data_list = input.split(' ')
data_uniq = {int(num): data_list.count(num) for num in data_list if data_list.count(num) > 1}
for itm in data_uniq.items():
    print(itm)

# functions
def make_a_sound():
    print('quack')

make_a_sound()

def agree():
    return True

if agree():
    print("agreed")
else:
    print("disagreed")

def say_anything(something):
    print(something)
say_anything("hello, world")

def commentary(color):
    if color == 'red':
        return 'It\'s a tomator'
    elif color == 'green':
        return 'It\'s a green pepper'
    elif color == 'bee purple':
        return 'I don\'t know what that is, but only bees can see it.'
    else:
        return 'I\'ve never heard of the color ' + color + ' before.'

print(commentary('red'))
print(commentary('green'))
print(commentary('bee purple'))
print(commentary('blue'))

# function positional arguments
def menu(wine, entree, dessert='bananas'):
    return {'wine':wine, 'entree':entree, 'dessert':dessert}
print(menu("chardonnay",'cake','chicken'))

print(menu(entree='chicken',dessert='cake',wine='merlot'))
print(menu(entree='chicken',wine='merlot'))

# positional arguments and Keyword arguments
def print_args(*args):
    'returns all positional arguments'
    print(args)
print(print_args())
print(print_args('one','two',3))

def print_kwargs(**kwargs):
    print('Keyword arguments: ', kwargs)
print(print_kwargs(wine='merlot',entree='mutton',dessert='cake'))

# docstrings
help(print_args)
print(print_args.__doc__)

# passing functions and args to functions
def answer():
    print(42)
answer()
def run_function(func):
    func()
run_function(answer)

print('###')

def addit(a,b):
    print(a + b)
addit(3,4)

def run_function_with_args(func,arg1,arg2):
    func(arg1,arg2)
run_function_with_args(addit,2,9)
print('###')

#closure
def outer_message(saying):
    def inner_message():
        return 'hello, there, ' + saying
    return inner_message

msg = outer_message('Clif')
print(msg())
msg = outer_message('Joann')
print(msg())

'''

# lambda

def capitalizeIt(msg, func):
    for word in msg:
        print(func(word))

capitalizeIt(['hello','clif','are','you','awake'],lambda word: word.upper())

def changeIt(num_list, func):
    for n in num_list:
        print(func(n))

changeIt([1,2,3,4,5,6,7,8,9,10,100,1000],lambda n: n+2)
changeIt([1,2,3,4,5,6,7,8,9,10,100,1000],lambda n: n**2)
