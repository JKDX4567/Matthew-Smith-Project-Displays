#include <Servo.h> 
#include <Wire.h>
#include <Ultrasonic.h>
#include "SSD1306.h"
#include <ESP8266WiFi.h>
#include "23F_PubSubClient.h"
#include "23F_WiFiManager.h"
#include <math.h>

#define motor1pin D0
#define motor2pin D2 

// Sets up the librarys and motor pins

Servo motor1; // Right one
Servo motor2; // Left one

// 95 to stop motor one (why??)

int count = 0;
int distance1; 
int distance2;
int distance3; 
Ultrasonic ultrasonic_center(D8, D5);      
Ultrasonic ultrasonic_right(D9, D6);   
Ultrasonic ultrasonic_left(D10, D7); 
// Variables for sensors



float targetDirection;
int x, y; //Coordinates
int x2, y2; // Spare coordinates
// Variables related to position and direction

SSD1306 display(0x3C, D14, D15); // Sets up Oled display with pins used
   

int motor_level1 = 113;
int motor_level2 = 72;
// make the motor go straight

float degreesPerSecond = 150; // Keeps changing??
float current_direction = 0;
// variables for making turns while keeping track of direction

float pi = 3.14159265358979323846;// Constant used to degrees


// Motor levels to make it go straight forward
// and degrees robot turns per second

float x_target[]= {1600.0, 2000.0, 110.0, 700.0}; 
float y_target[]= {130.0, 700.0, 150.0, 130.0}; 
int target_counter = 0;

// Lists and index to keep track of current target location


char payload_global[100];                     
boolean flag_payload; 
// Keeps track of the information given from the server
 
// MQTT Settings
const char* mqtt_server = "192.168.0.140";                                    
const char* MQusername = "user";                                                   
const char* MQpassword = "Stevens1870";      
const char* MQtopic= "louis_lidar_new";                                               
const int mqtt_port = 1883;   

// Wifi Settings
const char* ssid = "TP-Link_4DB1";
const char* password = "01747331";                                
 
WiFiClient espClient;                         
PubSubClient client(espClient);             
      
void setup_wifi() { 
  delay(10);
  // We start by connecting to a Stevens WiFi network
  WiFi.begin(ssid, password);           
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");                        
  }
  randomSeed(micros());                       
}

void callback(char* topic, byte* payload, unsigned int length) {
  for (int i = 0; i < length; i++) {
    payload_global[i] = (char)payload[i];
  }
  payload_global[length] = '\0';              
  flag_payload = true;                        
}
  // Function for recieving lidar information

void reconnect() {                                                                
  // Loop until we're reconnected
  while (!client.connected()) {
    // Create a random client ID
    String clientId = "ESP8266Client-";       
    clientId += String(random(0xffff), HEX);  
    // Attempt to connect                     
    if (client.connect(clientId.c_str(),MQusername,MQpassword)) {
      client.subscribe(MQtopic);             
    } else {
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}
// function for reconecting to the wifi

void set_direction(float direction_desire){
    float direction_difference = current_direction-direction_desire;
    // negative = turn left, positive = turn right
    float turn_time = direction_difference/degreesPerSecond;
    turn_time = turn_time * 1000;
    // determines how long to call delay function
    Serial.print("Di Di: ");
    Serial.println(direction_difference);
    Serial.print("TT: ");
    Serial.println(turn_time);
    if(turn_time > 0.00){
        motor_level1 = 180;
        motor_level2 = 180;

        motor1.write(motor_level1);
        motor2.write(motor_level2);
        turn_time = turn_time * -1;

        delay(turn_time);
      }
      else if(turn_time < 0.00){
        motor_level1 = 0;
        motor_level2 = 0;

        motor1.write(motor_level1);
        motor2.write(motor_level2);

        delay(turn_time);

      }
        motor_level1 = 95;
        motor_level2 = 90;

        motor1.write(motor_level1);
        motor2.write(motor_level2);
  }

// This function takes a given direction, and makes the robot
// Turn in that direction

float findTargetDirection(float uX, float uY, float tX, float tY){
    float difX;
    float difY;
    float angleNRad;
    float angleNDeg;
    difX = tX-uX;
    Serial.print("difx: ");
    Serial.println(difX);
    difY = tY-uY;
    Serial.print("dify: ");
    Serial.println(difY);
    if (difY == 0){
        if(difX >0){
            return 0.0;
          }
         else if(difX<0){
            return 180.0;
          }
    }
    // If they are on the same y, either goes right or left
    else if(difX == 0){
        if(difY >0){
            return 90.0;
          }
         else if(difY<0){
            return 270.0;
          }
      }
      // If they are on the same x, either go up or down
      else{
          angleNRad = atan(difY/difX);
          angleNDeg = (angleNRad * 180)/pi;
          Serial.print("Angle Rad: ");
          Serial.println(angleNRad);
          Serial.print("Angle degree: ");
          Serial.println(angleNDeg);
          if(difX < 0 && difY < 0){
            return 180.0+angleNDeg;
          }
          else if(difX < 0 && difY > 0){
            return 180.0+angleNDeg;
          }
          else if(difX > 0 && difY < 0){
            return 360.0+angleNDeg;
          }
          else if(difX > 0 && difY > 0){
            return angleNDeg;
          }
       }
       // Decides what to do with the angle (quadrant 3, 2, 4, 1 in that order)     
}
// Finds the angle between the robot and the target (assuming its on the x axis)
// Check march 23 sketch for math tests

void changeToTarget(){
    targetDirection = findTargetDirection(x, y, x_target[target_counter], y_target[target_counter]);
    if ((targetDirection-current_direction<10) && (targetDirection-current_direction>-10)){
      set_direction(targetDirection);
      current_direction = targetDirection;
    }
}
  // Helper function just meant to change to target direction
void preventOver(){
    distance1 = ultrasonic_center.read(CM)*10;
    distance2 = ultrasonic_right.read(CM)*10;
    distance3 = ultrasonic_left.read(CM)*10;
    while(distance1 > 3500 || distance2 > 3500 || distance3 > 3500 || distance1 <= 3 || distance2 <= 3 || distance3 <= 3){
      motor_level1 = 95;
      motor_level2 = 90;
       motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(250);
        distance1 = ultrasonic_center.read(CM)*10;
        distance2 = ultrasonic_right.read(CM)*10;
        distance3 = ultrasonic_left.read(CM)*10;
        Serial.println("Distance 1: " + String(distance1));
        Serial.println("Distance 2: " + String(distance2));
        Serial.println("Distance 3: " + String(distance3));
    }
  }// Makes sure the sensors are not reading something stupid

void collectCord(){
  String payload(payload_global);              
  int testCollector[10];                      
  int count = 0;
  int prevIndex, delimIndex;
    
  prevIndex = payload.indexOf('[');           
  while( (delimIndex = payload.indexOf(',', prevIndex +1) ) != -1){
    testCollector[count++] = payload.substring(prevIndex+1, delimIndex).toInt();
    prevIndex = delimIndex;
  }
  delimIndex = payload.indexOf(']');
  testCollector[count++] = payload.substring(prevIndex+1, delimIndex).toInt();
    
  //Robot location x,y from MQTT subscription variable testCollector 
  x = testCollector[0];
  y = testCollector[1];
  Serial.print(x);
  Serial.println("x-cord");
  Serial.print(y);
  Serial.println("y-cord");

 }
 
 // Records the x and y coordinate from the information recieved

float angleFind(int x1, int y1, int x2, int y2){
  float num1, num2;
  // 2 = current, 1 = past
    if (x2>x1 && y2>y1){
        num1 = atan((y2-y1)/(x2-x1));
        num2 = 360 - ((num1 * 180)/pi);
        return  num2;
      }
      // quadrant 1
     else if (x2<x1 && y2>y1){
        num1 = atan((y2-y1)/(x1-x2));
        Serial.println("num1: " + String(num1));
        num2 = 360 - ((num1 * 180)/pi);
        Serial.println("num2: " + String(num2));
        return  num2;
      }
      // quadrant 2
      else if (x2<x1 && y2<y1){
        num1 = atan((y1-y2)/(x1-x2));
        num2 = 180 + ((num1*180)/pi);
        return num2;
      }
      // quadrant 3
      else if (x2>x1 && y2<y1){
        num1 = atan((y1-y2)/(x1-x2));
        num2 = 360 - ((num1 * 180)/pi);
        return  num2;
      }
      // quadrant 4
      
  }
  // Given 2 coordinates, finds the angle it makes with respect to the x axis
void setup()
{
  Serial.begin(115200);
  // Set up the baud rate and serial moniter (needs to be first)
  setup_wifi();                               
  delay(3000);
  Serial.println("Wemos POWERING UP ......... ");
  client.setServer(mqtt_server, mqtt_port);          //This 1883 is a TCP/IP port number for MQTT 
  client.setCallback(callback); 
  
  display.init();
  display.flipScreenVertically();
  display.display();
  // Sets up the Oled 
       
      motor1.attach(motor1pin); 
      motor2.attach(motor2pin);
      // sets up motors
      
}


void loop(){ 
  if (!client.connected()){
    Serial.print("...");
    reconnect();
    }
    // If not connected, reconnect
      Serial.println("Completed reconnect");

// call only the first time, and is used to determine the degreesPerSecond (or how much the robot turns in a single second of turning.
  
  client.loop();   
   display.clear();
  display.drawString(0,0,"x:  " + String(x));
  display.drawString(0,20,"y: " + String(y));
  display.display();
Serial.println("Complete recording");

  collectCord();
  changeToTarget();
    }
  // Records the robots current direction, figured out where the target is, and makes the robot turn that way



  distance1 = ultrasonic_center.read(CM)*10;
  distance2 = ultrasonic_right.read(CM)*10;
  distance3 = ultrasonic_left.read(CM)*10;
  // reads the sensors
 // preventOver();
 /*
    while (distance1 < 60 || distance2 < 60 || distance3 < 60){
        if (distance1 < 60){
        motor_level1 = 95;
        motor_level2 = 90;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        // Stops
        motor_level1 = 72;
        motor_level2 = 113;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(400);
        // Goes backwords
        motor_level1 = 180;
        motor_level2 = 180;
         motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        current_direction = current_direction + (degreesPerSecond*.5);
        // Turns left
        motor_level1 = 113;
        motor_level2 = 72;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(200);
        // Goes straight for a bit)
        Serial.println("Going backwards " + String(distance1)); 
        collectCord();
        changeToTarget();
        motor_level1 = 95;
        motor_level2 = 90;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        preventOver();
        
    }
    // Go backwards then change your position a bit
   else if (distance3 < 60){
        motor_level1 = 95;
        motor_level2 = 90;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        // Stops
        motor_level1 = 180;
        motor_level2 = 180;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        current_direction = current_direction + (degreesPerSecond*2);
        delay(2000);
        // Turn left a lot (to turn right)
        motor_level1 = 113;
        motor_level2 = 72;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(600);
        // Goes straight for a bit)
        Serial.println("Going right " + String(distance2));
        collectCord();
        changeToTarget();
          motor_level1 = 113;
        motor_level2 = 72;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(600);
        }
        motor_level1 = 95;
        motor_level2 = 90;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        preventOver();
        
   }
      // Turn right and change your position a little
      
    else if (distance2 < 60){
        motor_level1 = 95;
        motor_level2 = 90;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        // Stops
        motor_level1 = 180;
        motor_level2 = 180;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        current_direction = current_direction + (degreesPerSecond*0.25);
        delay(250);
        // Turn right
        motor_level1 = 113;
        motor_level2 = 72;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(600);
        // Goes straight for a bit)
        Serial.println("Going right " + String(distance2)); 
        collectCord();
        changeToTarget();
          motor_level1 = 113;
        motor_level2 = 72;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(600);
        }
        motor_level1 = 95;
        motor_level2 = 90;
        motor1.write(motor_level1);
        motor2.write(motor_level2);
        delay(500);
        preventOver();
        
        
      }
      // Turn left and change your position a little
    }
      // Turn left and change your position a little
// First thing the robot does is get itself out of running into something in the while loop
*/
x2 = x;
y2 = y;
motor_level1 = 113;
motor_level2 = 72;
motor1.write(motor_level1);
motor2.write(motor_level2);
delay(1000);
 Serial.println("one loop");
 Serial.println("Motor level: " + String(motor_level1));
Serial.println("Distance 1: " + String(distance1));
Serial.println("Distance 2: " + String(distance2));
Serial.println("Distance 3: " + String(distance3));
// Do basic movement forward
collectCord();
current_direction = angleFind(x2, y2, x, y);
// Makes the robot move forward, and records where it was before and after moving straight, finding the angle it is currently facing

  if((x - x_target[target_counter] < 50 && x - x_target[target_counter] > - 50)
   && (y - y_target[target_counter] < 50 && y - y_target[target_counter] > - 50)){
    motor_level1 = 95;
    motor_level2 = 90;
    motor1.write(motor_level1);
    motor2.write(motor_level2);
    delay(3000);
    collectCord();
    display.clear();
    display.drawString(0,0,"x:  " + String(x));
    display.drawString(0,20,"y: " + String(y));
    display.display();

    Serial.println("Complete recording");
    target_counter = target_counter + 1;
    changeToTarget();
      // if on a target, stops for three seconds and turns toward the next target
    if(target_counter >= 4){
      motor_level1 = 95;
      motor_level2 = 90;
      motor1.write(motor_level1);
      motor2.write(motor_level2);
      delay(200000000);
      }
      // if on the last target, just stop
   }
   // Check to see if on a target

}
