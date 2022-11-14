<?php   
  require_once 'connection.php';  
 # $response = array to store state variables like 'error' 'message' 'user'
  $response = array();  
  if(isset($_GET['apicall'])){  
  switch($_GET['apicall']){  
  case 'signup':  
    if(isTheseParametersAvailable(array('firstname','middlename','lastname','email','phone'))){  
    $firstname = $_POST['firstname'];   
    $middlename = $_POST['middlename'];   
    $lastname = $_POST['lastname'];  
    $email = $_POST['email'];   
    $phone = $_POST['phone'];   

    $stmt = $conn->prepare("SELECT id FROM register WHERE phone = ?");  
    $stmt->bind_param("s", $phone);  
    $stmt->execute();  
    $stmt->store_result();  
   
// if($stmt->mysql_insert_id();))
    if($stmt->num_rows > 0){  
        $response['error'] = true;  
        $response['message'] = 'User already registered';  
        $stmt->close();  
    }  
    else{  
    $stmt = $conn->prepare("INSERT INTO register (firstname, middlename, lastname, email, phone) VALUES (?, ?, ?, ?, ?)");  
        $stmt->bind_param("sssss", $firstname, $middlename, $lastname, $email, $phone);  
   
        if($stmt->execute()){  
            $stmt = $conn->prepare("SELECT id,firstname,middlename,lastname,email,phone FROM register WHERE phone = ?");   
            $stmt->bind_param("s",$phone);  
            $stmt->execute();  
            $stmt->bind_result($id, $firstname, $middlename, $lastname,$email, $phone);  
            $stmt->fetch();  
     
            $user = array(  
            'id'=>$id,   
            'firstname'=>$firstname, 
            'middlename'=>$middlename, 
            'lastname'=>$lastname, 
            'email'=>$email,  
            'phone'=>$phone  
            );  
   
            $stmt->close();  
   
            $response['error'] = false;   
            $response['message'] = 'User registered successfully';   
            $response['user'] = $user;   
        }  
    }  
   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available bro';   
}  
break;   
case 'login':  

  if(isTheseParametersAvailable(array('phone'))){  
    $phone = $_POST['phone'];  

   
    $stmt = $conn->prepare("SELECT id, firstname,middlename,lastname, email, phone FROM register WHERE phone = ?");  
    $stmt->bind_param("s",$phone);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id, $firstname,$middlename,$lastname, $email, $phone);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,   
    'firstname'=>$firstname,  
    'middlename'=>$middlename,  
    'lastname'=>$lastname,   
    'email'=>$email,  
    'phone'=>$phone  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'Login successful !!';   
    $response['user'] = $user;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid username or password';  
 }  
}  
break; 
case 'userdetail':  

  //if(isTheseParametersAvailable(array('id'))){  
    $id = $_GET['id'];  

   
    $stmt = $conn->prepare("SELECT id, firstname,middlename,lastname, email, phone FROM register WHERE id = ?");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id, $firstname,$middlename,$lastname, $email, $phone);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,   
    'firstname'=>$firstname,  
    'middlename'=>$middlename,  
    'lastname'=>$lastname,   
    'email'=>$email,  
    'phone'=>$phone  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'Data Fetch successfull';   
    $response['user'] = $user;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break;   
case 'bannerAds':  

  //if(isTheseParametersAvailable(array('id'))){  
  

   // $id = $_GET['id'];  
    $stmt = $conn->prepare("SELECT id, image as url  FROM banners WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$url);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,   
    'url'=>IMGPATH.$url  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'banners Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break;   
default:   
 $response['error'] = true;   
 $response['message'] = 'Invalid Operation Called';  
}  
}  
else{  
 $response['error'] = true;   
 $response['message'] = 'Invalid API Call';  
}  
echo json_encode($response);  
function isTheseParametersAvailable($params){  
foreach($params as $param){  
 if(!isset($_POST[$param])){  
     return false;   
  }  
}  
return true;   
}  
?>  
