def small_birds_array():
    '''
    #
    #
        playing with arrays
    #
    #
    '''

    small_birds = ['canary','parrot','finch']
    print('small birds:\n',small_birds)

    extinct_birds = ['emu','ostrich','king penguin']
    print('extinct birds:\n',extinct_birds)

    small_birds.append(extinct_birds)
    print('appending extinct list as element into small birds:\n', small_birds)

    small_birds.extend(extinct_birds)
    print('extending small birds by adding items from extinct birds:\n',small_birds)

    del small_birds[3]
    print('after removing extinct bird list element:\n',small_birds)

def display_vowels():
    '''
    #
    #
        using a simple for-loop
    #
    #
    '''
    vowels = 'aeiou'
    print('find the vowels "e" and "i" in aeiou')
    for v in vowels:
    	if v == 'e' or v == 'i':
    		print(v,' is a vowel')
    	else:
            print(v,' is NOT a vowel')

def use_simple_tuple():
    '''
    #
    #
        Use for loop to traverse a tuple
    #
    #
    '''
    names_tuple = ('clif','joann','sirena','madison')
    print('tuple: ',names_tuple)
    for n in names_tuple:
        print(n)

def use_simple_dictionary():
    '''
    #
    #
        Use for loop to traverse dictionary
    #
    #
    '''
    title_tuple = ('dad','mom','kid')
    print('title tuple:\n',title_tuple)

    names_tuple = ('clif','joann','sirena')
    print('names tuple:\n',names_tuple)

    names_dict = dict(zip(title_tuple,names_tuple))
    print('tuple combined to make a dictionary:\n',names_dict)

    print('titles:\n',names_dict.keys())
    print('names:\n',names_dict.values())

    for i in names_dict.items():
        print('t,n: ',i)

if __name__ == '__main__':
    print(small_birds_array.__doc__)
    small_birds_array()
    print(display_vowels.__doc__)
    display_vowels()
    print(use_simple_tuple.__doc__)
    use_simple_tuple()
    print(use_simple_dictionary.__doc__)
    use_simple_dictionary()
