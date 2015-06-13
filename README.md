# Raspi
A small Java application with the Pi4J library to control the GPIO pins on a Raspberry Pi

The programming was done with the following setup in mind
<ul>
  <li>A broadboard with connected LED lights and resistors</li>
  <li>A photo cell with connected capacitor</li>
  <li>A Raspberry Pi</li>
  <li>A bunch of jumper wires</li>
</ul>

By reading the value of the photo cell the lights light up. A darker environment makes a higher value which makes more LEDs ligt up. The values chosen here were chosen according to the light level by my desk and might have to be calibrated according to your environment. See below for how to connect it.

<h3>How to run it?</h3>
Start by installing Pi4J on your Raspberry Pi

```
curl -s get.pi4j.com | sudo bash
sudo apt-get update
sudo update-get upgrade
```
Compile the code either on your Raspberry Pi or on your desktop. The easiest way if you are on a desktop is to use an IDE and create a maven project and include the following dependency in your pom.xml
```
<dependency>
	<groupId>com.pi4j</groupId>
	<artifactId>pi4j-core</artifactId>
	<version>1.0</version>
</dependency>
```
Then build the code and transfer the generated class files to your Raspberry Pi in a location of your choice. If you want to compile it on your Raspberry Pi or desktop without using an IDE I guess you could install the maven dependency on your own but unfortunately I can't really help you there as I'm not too experienced with maven.

When you have your compiled classes on your Raspberry Pi change the directory to their location and run the following command to execute.
```
sudo java -classpath .:classes:/opt/pi4j/lib/'*' Raspi
```
The application runs until you enter something on your keyboard and press enter. It might be a good idea to do this instead of killing the application as the pins will remain high and the lights will still be on if they were before.

<h3>Finally</h3>
Have fun!

<img src="https://cloud.githubusercontent.com/assets/3191489/8144396/691b9a92-11dd-11e5-984a-12b1e67b0ff3.jpg"></img>
<img src="https://cloud.githubusercontent.com/assets/3191489/8144402/a4232df8-11dd-11e5-9078-766c9ac51855.jpg"></img>


