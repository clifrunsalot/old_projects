#ifndef __HELLO__
#define __HELLO__

class Hello {
	private:
		char * msg;

	public:
		Hello();
		~Hello();
		void sayMessage();
		void setMessage(char *);
		char * getMessage();
};

#endif
