import bdb

class Tdb(bdb.Bdb):
    def user_call(self, frame, args):
        name = frame.f_code.co_name
        if not name: name = '???'
        fn = self.canonic(frame.f_code.co_filename)
        __observer.invoke(frame.f_lineno, 0, name,"","")
    def user_line(self, frame):
        name = frame.f_code.co_name
        if not name: name = '???'
        fn = self.canonic(frame.f_code.co_filename)
        __observer.invoke(frame.f_lineno, 0, name,"","")
    def user_return(self, frame, retval):
        name = frame.f_code.co_name
        if not name: name = '???'
        fn = self.canonic(frame.f_code.co_filename)
        __observer.invoke(frame.f_lineno, 0, name,"","")
    def user_exception(self, frame, exc_stuff):
        name = frame.f_code.co_name
        if not name: name = '???'
        fn = self.canonic(frame.f_code.co_filename)
        __observer.invoke(frame.f_lineno, 0, name,"","")
        self.set_continue()