<?php  
$servername = "localhost";  
$username = "root";  
$password = "";  
#below is our db name on mysql --- comment
$database = "affetta";  
$conn = new mysqli($servername, $username, $password, $database);  
if ($conn->connect_error) {  
    die("Connection failed: " . $conn->connect_error);  
}  

define("IMGPATH", "https://affetta.com/cms/");
define("UPLOADPATH", "http://192.168.0.104/affetta_api/");
?> 

