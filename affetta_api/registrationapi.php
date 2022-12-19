<?php   
  require_once 'connection.php';  
 // $response = array to store state variables like 'error' 'message' 'user'
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
            $stmt = $conn->prepare("INSERT INTO register (firstname,middlename, lastname, email, phone) VALUES (?, ?, ?, ?, ?)");  
            $stmt->bind_param("sssss",$firstname ,$middlename,$lastname, $email, $phone);  

            if($stmt->execute()){  
                $stmt = $conn->prepare("SELECT id,firstname,middlename,lastname,email,phone FROM register WHERE phone = ?");   
                $stmt->bind_param("s",$phone);  
                $stmt->execute();  
                $stmt->bind_result($id,$firstname, $middlename,$lastname,$email, $phone );  
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
        $response['message'] = 'required parameters are not available';   
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
    $response['message'] = 'This phone number is not registered in AFFETTA App';  
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
      

    $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['url'] =IMGPATH.$url ;
 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'banners Fetch successfull';   
    $response['user'] = $banner_data;   
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
    $stmt->bind_result($id,$heading,$url);  
    $stmt->fetch();  
      

    $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $heading;
 $temp['url'] =IMGPATH.$url ;
 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'categories Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break;  
//---------------------------------------------------------------------------------------------------------------------------

case 'subCategories':  

    $stmt = $conn->prepare("SELECT id,  subcategory_name  FROM subcategories WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

 if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$heading);  
    $stmt->fetch();  
      

    $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $heading; 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'Sub categories Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break;  
// //-------------------------------------------------------------------------------------------------- 
 case 'featured':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.featured='1'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();

 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'Featured products Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break; 
// //-------------------------------------------------------------------------------------------------- 
case 'offerBanners':  

    $stmt = $conn->prepare("SELECT id, image  FROM offerbanners WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$url);  
    $stmt->fetch();  
      

    $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['url'] =IMGPATH.$url ;
 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'Offer Banners Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break;  
// //-------------------------------------------------------------------------------------------------- 
case 'brands':  

    $stmt = $conn->prepare("SELECT id, sch_name as heading , image  FROM school_name WHERE active='0'");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$heading,$url);  
    $stmt->fetch();  
      

    $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $heading;
 $temp['url'] =IMGPATH.$url ;
 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'brands Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break;  

// //--------------------------------------------------------------------------------------------------  
case 'deals':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.deals='1'");  
    //$stmt->bind_param("s",$id);  
   $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();

 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = ' Hot Deals Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break; 
  
// --------------------------------------------------------------------------------------------------  
case 'comboOffers':  


 $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.subcategory='combo' ");  
    //$stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();

 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = ' Combo Offers Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break; 

// //-------------------------------------------------------------------------------------------- 
case 'newArrivals':  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.new_arrival='1' ");  
    //$stmt->bind_param("s",$id);  
  $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();

 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = ' New Arrivals Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break; 






// //-------------------------------------------------------------------------------------------- 
case 'getMyCart':  
$id = $_POST['id'];

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1,c.quantity from cart c left JOIN products pd on pd.productid=c.productid LEFT join product_detail_description pdd on pdd.id=c.product_desc_id where c.status='Added in cart' and c.userid=? ");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();   
    $stmt->store_result(); 

  if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image,$quantity);  
  
 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;
  $temp['quantity'] = $quantity;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'Cart Detail Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'No Record Found';  
 }  
//}  
//}  
break; 





//-------------------------------------------------------------------------------------------- 
case 'getMyWishlist':
$id = $_POST['id'];  

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1,c.quantity from wishlist c left JOIN products pd on pd.productid=c.productid LEFT join product_detail_description pdd on pdd.id=c.product_desc_id
 where c.status='Created' and c.userid=?");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

      if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image,$quantity);  


 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'Wishlist Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'No Record Found';  
 }  
//}  
//}  
break; 





  //----------------------------------------------------------------------------- 
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

//---------------------------------------------------------------------------------------------------------------------------------
case 'getParticularCategoryProducts' :  
$category = $_POST['category'];   
$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.category=? ");  
   
    $stmt->bind_param("s",$category);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc,$image);  
    $stmt->fetch();

 $banner_data = array();
 
 while($stmt->fetch()){
 $temp = array(); 
 $temp['id'] = $id; 
 $temp['heading']= $name;
 $temp['sale'] =$sale;
 $temp['disc']=$disc;
 $temp['mrp']=$mrp;
  $temp['image'] =IMGPATH.$image ;

 
 array_push($banner_data, $temp);
 }
   
    $response['error'] = false;   
    $response['message'] = 'Particular category products Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break; 

   //---------------------------------------------------------------------------------
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









