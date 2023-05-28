<?php   
require_once 'connection.php';  
// $response = array to store state variables like 'error' 'message' 'user'
$response = array();  
if(isset($_GET['apicall'])){  
    switch($_GET['apicall']){  

//---------------------------------------------------------------------------------------------------------------------------------

        case 'sendotp':  
        if(isTheseParametersAvailable(array('phone_no'))){

            $phone_no = $_POST['phone_no'];
            $active = '0'; 
            $error=$success='';

            $stmt = $conn->prepare("select id, fullname from register where phone = ? and active = ?");  
            $stmt->bind_param("ss", $phone_no, $active); 
            $stmt->execute();  
            $stmt->store_result();  
            if($stmt->num_rows > 0){  

              $digits = 5;
              $otp = rand(pow(10, $digits-1), pow(10, $digits)-1);
              $curl = curl_init();

              curl_setopt_array($curl, array(
                CURLOPT_URL => 'https://sms.authlinq.com/api_v2/message/send',
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_ENCODING => '',
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 0,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => 'POST',
                CURLOPT_POSTFIELDS =>'{
                  "dlt_template_id":"1407159974855144720",
                  "sender_id":"AUTOTP",
                  "mobile_no":"'.$phone_no.'",
                  "message":"OTP for Ongoing Login Transaction on today is '.$otp.' and valid till next 10 minutes. Do not share this OTP to anyone for security reasons."
              }',
              CURLOPT_HTTPHEADER => array(
                  'Authorization: Bearer YUixku5p8C-jEGVkhSVXV6xSSF06pPKfRgd5PKEfrLU690PqyYyx1pNBZZ9mcV6-',
                  'Content-Type: application/json'
              ),
          ));

              $response['otpp']= curl_exec($curl);
      //print_r($response);die();

              curl_close($curl);

              $stmt_new = $conn->prepare("update register set sms_otp=? where phone=?");  
              $stmt_new->bind_param("ss", $otp ,$phone_no); 
              $stmt_new->execute();

              $success = "OTP sent to your registered mobile no - ".$phone_no;

              $response['error'] = false;   
              $response['message'] = "$success";  
          } 

          else
          {
           $response['error'] = true;   
           $response['message'] = 'This number is not registered';   
       }
   }  
   else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 

//--------------------------------------------------------------------------------------------------------------------------------
case 'verifyotp':  
if(isTheseParametersAvailable(array('phone_no'))){

    $phone_no = $_POST['phone_no'];
    $otp = $_POST['otp'];
    $active = '0'; 
    $error=$success='';

    $stmt = $conn->prepare("select sms_otp,id from register where 
        phone = ? AND active = ?");  
    $stmt->bind_param("ss", $phone_no, $active); 
    $stmt->execute();  
    $stmt->bind_result($new_otp,$new_id);
            $stmt->store_result(); 

     $stmt->fetch();  
     // when i used store_result()  , it didn't gave me any o/p
     // but when i used  $stmt->fetch();    it gave me result.
    $stmt->store_result();  
    if($stmt->num_rows > 0){
        if ($new_otp == $otp) {

            $last_login_ip =  $_SERVER['REMOTE_ADDR'];
            $last_login =  date('Y-m-d H:i:s');
            $computer_name =  gethostname();
            $last_logout =  '0000-00-00 00:00:00';

 $stmts = $conn->prepare("UPDATE register SET last_login_ip=?, last_login=?,computer_name=?,last_logout=? WHERE phone = ? ");
   
    $stmts->bind_param("sssss",$last_login_ip,$last_login,$computer_name,$last_logout,$phone_no);  
       // echo("Hello USER");die();


    $stmts->execute();  

           $success = "OTP Verified Successfully";
           $response['error'] = false;   
           $response['message'] = $success;  

           //  $machine_ip = $_SERVER['REMOTE_ADDR'];
           // $computer_name = gethostname();
       }
       else{
            $response['error'] = true;   
            $response['message'] = 'OTP is invalid';   
       }

   } 

   else
   {
       $response['error'] = true;   
       $response['message'] = 'This number is not registered';   
   }
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 

//--------------------------------------------------------------------------------------------------------------------------------
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