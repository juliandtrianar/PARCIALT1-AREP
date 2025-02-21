# PARCIALT1-AREP

# TALLER DE VERIFICACIÓN DE CONOCIMIENTOS TÉCNICOS

## Iniciando 

1. Descargue el siguiente repositorio 
 ``` 
git clone https://github.com/juliandtrianar/PARCIALT1-AREP.git
 ``` 
2. Ingrese al directorio del proyecto
 ``` 
cd PARCIALT1-AREP
 ``` 
3. Compile el proyecto 
 ``` 
javac -d target/classes src/main/java/org/edu/eci/arep/*.java

 ``` 
4. Abra dos líneas de comandos para los siguientes pasos:
5. Ejecute el servidor API de la calculadora con el siguiente comando 
 ``` 
java -cp target/classes org.edu.eci.arep.ReflexCalculator
 ``` 
6. Ejecute el servidor fachada con el siguiente comando 
 ``` 
java -cp target/classes org.edu.eci.arep.ServiceFacade

 ``` 
  
## Probando la aplicación

Ingrese a la siguiente dirección: 

http://localhost:35001/compreflex=bbl(5,3,8,4,2)
http://localhost:35001/compreflex=sin(1.5)

