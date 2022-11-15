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
//------------------------------------------------------------------------------------------------------  
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
//-----------------------------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------------------------------
case 'bannerAds':  

  //if(isTheseParametersAvailable(array('id'))){  
  
//active='0'
    // $id = $_GET['id'];  
    $stmt = $conn->prepare("SELECT id, image as url  FROM banners WHERE active='0' ");  
    // $stmt->bind_param("s",$id);  
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
//------------------------------------------------------------------------------------------------------
case 'categories':  

    $stmt = $conn->prepare("SELECT id, category_name as heading , image  FROM categories WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$heading,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$heading,   
    'image'=>IMGPATH.$image  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'categories Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//-------------------------------------------------------------------------------------------------- 
case 'featured':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.featured='1'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$name,   
    'mrp'=>$mrp,   
    'sale'=>$sale,   
    'disc'=>$disc,   
    'image'=>IMGPATH.$image 

    );  
   
    $response['error'] = false;   
    $response['message'] = 'Featured products Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//-------------------------------------------------------------------------------------------------- 
case 'offerBanners':  

    $stmt = $conn->prepare("SELECT id, image  FROM offerbanners WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id, 
    'image'=>IMGPATH.$image  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'offer banners Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//-------------------------------------------------------------------------------------------------- 
case 'brands':  

    $stmt = $conn->prepare("SELECT id, sch_name as heading , image  FROM school_name WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$heading,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$heading,   
    'image'=>IMGPATH.$image  
    );  
   
    $response['error'] = false;   
    $response['message'] = 'brands Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//--------------------------------------------------------------------------------------------------  
case 'hotDeals':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.deals='1'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();  
    $user = array(  
     'id'=>$id,
    'heading'=>$name,   
    'mrp'=>$mrp,   
    'sale'=>$sale,   
    'disc'=>$disc,   
    'image'=>IMGPATH.$image 
    );  
   
    $response['error'] = false;   
    $response['message'] = 'hot deals Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//--------------------------------------------------------------------------------------------------  
case 'comboOffers':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.subcategory='combo' ");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$name,   
    'mrp'=>$mrp,   
    'sale'=>$sale,   
    'disc'=>$disc,   
    'image'=>IMGPATH.$image 
    );  
   
    $response['error'] = false;   
    $response['message'] = 'combo offers Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//-------------------------------------------------------------------------------------------- 
case 'newArrivals':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.new_arrival='0' ");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$name,   
    'mrp'=>$mrp,   
    'sale'=>$sale,   
    'disc'=>$disc,   
    'image'=>IMGPATH.$image 
    );  
   
    $response['error'] = false;   
    $response['message'] = 'new arrivals Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break; 
//-------------------------------------------------------------------------------------------- 
case 'getMyCart':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id 
left join cart c on pd.productid=c.product_id 
 where userid='?' and c.status='Added tot cart' ");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$name,   
    'mrp'=>$mrp,   
    'sale'=>$sale,   
    'disc'=>$disc,   
    'image'=>IMGPATH.$image 
    );  
   
    $response['error'] = false;   
    $response['message'] = 'new arrivals Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  

//}  
break; 
//-------------------------------------------------------------------------------------------- 
case 'getMyWishlist':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id 
left join cart c on pd.productid=c.product_id 
 where userid='?' and c.status='Added tot cart' ");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'heading'=>$name,   
    'mrp'=>$mrp,   
    'sale'=>$sale,   
    'disc'=>$disc,   
    'image'=>IMGPATH.$image 
    );  
   
    $response['error'] = false;   
    $response['message'] = 'my wishlist Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  

//}  
break; 
//-------------------------------------------------------------------------------------------- 
case 'getMyOrders':  

    $stmt = $conn->prepare("SELECT * from bill_details where customer_id='?'");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$invoice_no,$total_amount,$customer_address,$shipping_address);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,
    'total_amount'=>$total_amount,   
    'customer_address'=>$customer_address,   
    'shipping_address'=>$shipping_address,   
    );  
   
    $response['error'] = false;   
    $response['message'] = 'my orders Fetch successfull';   
    $response['user'] = json_encode($user);   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  

//}  
break; 
//-------------------------------------------------------------------------------------------- 
case 'addToCart':  
    if(isTheseParametersAvailable(array('id','middlename','lastname','email','phone'))){  
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











<!-- "SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.new_arrival='0' "



"SELECT * from cart WHERE id = '?' and status='Added in cart'"



"SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id 
left join cart c on pd.productid=c.product_id 
 where userid='?' and c.status='Added tot cart' "


 -->

