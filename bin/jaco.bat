@echo off
rem call java interpreter
java -Djava.endorsed.dirs="/Users/stuartdouglas/workspace/JacORB/lib" -Djacorb.home="/Users/stuartdouglas/workspace/JacORB" -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -classpath "%CLASSPATH%" %*
