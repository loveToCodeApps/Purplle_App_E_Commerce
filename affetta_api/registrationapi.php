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






// //-------------------------------------------------------------------------------------------- c.status='Added in cart'
 case 'getMyCart':  
$id = $_POST['id'];
// $prod_desc_id = $_POST['prod_desc_id'];
// $prod_id = $_POST['prod_id'];

    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1,c.quantity from cart c left JOIN products pd on pd.productid=c.productid LEFT join product_detail_description pdd on pdd.id=c.product_desc_id where c.userid=? and c.status='Added in cart'");  
     
//, $prod_id,$prod_desc_id
 //and pd.productid=? and pdd.id = ?
   
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
    if(isTheseParametersAvailable(array('prod_id','user_id'))){  
    $prod_id = $_POST['prod_id'];   
    $prod_desc_id = $_POST['prod_desc_id'];   
    $user_id = $_POST['user_id'];
    $unit_price = $_POST['unit_price'];
    $total_price = $_POST['total_price'];
    $confirm_mobile = $_POST['confirm_mobile'];

// move_uploaded_file($ref_img['tmp_name'], UPLOAD_PATH . $ref_img['name']);
        $stmt = $conn->prepare("INSERT INTO cart (productid, userid,product_desc_id,unit_price,total_price,confirm_mobile) VALUES (?, ?, ?, ?, ?, ?)");  
        $stmt->bind_param("ssssss", $prod_id ,$user_id, $prod_desc_id,$unit_price,$total_price,$confirm_mobile);  

        $stmt->execute();
            $stmt->close();  



            $response['error'] = false;   
            $response['message'] = 'Added to cart successfully';   
            $response['user'] = "";   

        }  

    
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//---------------------------------------------------------------------------------------------------------------------------------
case 'addToWishList':  
    if(isTheseParametersAvailable(array('prod_id','user_id'))){  
    $prod_id = $_POST['prod_id'];   
    $user_id = $_POST['user_id'];   
// move_uploaded_file($ref_img['tmp_name'], UPLOAD_PATH . $ref_img['name']);
        $stmt = $conn->prepare("INSERT INTO wishlist (productid, userid) VALUES (?, ?)");  
        $stmt->bind_param("ss", $prod_id , $user_id);  

        $stmt->execute();
            $stmt->close();  



            $response['error'] = false;   
            $response['message'] = 'Added to wishlist successfully';   
            $response['user'] = "";   

        }  

    
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 

//-----------------------------------------------------------------------------------------------------------------------------------    
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

case 'getParticularBrandProducts' :  
$brand = $_POST['brand'];   
$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1 from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.dressmadefor=? ");  
   
    $stmt->bind_param("s",$brand);  
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
    $response['message'] = 'Particular Brand products Fetch successfull';   
    $response['user'] = $banner_data;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
//}  
break; 

   //------------------------------------------------------------------------------------------------------------------------------


//when we click on any product this page will open up.
case 'getProductDetailDescription':  

  //if(isTheseParametersAvailable(array('id'))){  
    $id = $_POST['id'];  

   
    $stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.image1,pd.image2 , pd.image3 , pd.image4 , pd.image5 , pd.image6 , pdd.item_desc , pdd.id from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.productid=? ");  
    $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
    $stmt->bind_result($id, $heading,$mrp,$sale, $image1, $image2,$image3,$image4,$image5,$image6,$disc,$prod_desc_id);  
    $stmt->fetch();  
    $user = array(  
    'id'=>$id,   
    'heading'=>$heading,  
    'sale'=>$sale,  
    'disc'=>$disc,   
    'mrp'=>$mrp,  
    'image1'=>IMGPATH.$image1,
    'image2'=>IMGPATH.$image2,
    'image3'=>IMGPATH.$image3,
    'image4'=>IMGPATH.$image4,
    'image5'=>IMGPATH.$image5,
    'image6'=>IMGPATH.$image6,
 
    'prod_desc_id'=>$prod_desc_id


    );  
   
    $response['error'] = false;   
    $response['message'] = 'Product description fetch successfull';   
    $response['user'] = $user;   
 }  
 else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
 }  
//}  
break;   
//-----------------------------------------------------------------------------------------------
case 'editMyProfile':  


if(isTheseParametersAvailable(array('first_name','last_name','email','user_id','full_name','shipping_state','shipping_city','shipping_zipcode','shipping_whatsappno','billing_whatsappno'))){ 
    $first_name = $_POST['first_name'];    
    $last_name = $_POST['last_name']; 
    $email = $_POST['email']; 
    $user_id = $_POST['user_id']; 
    $shipping_state = $_POST['shipping_state']; 
    $shipping_city = $_POST['shipping_city']; 
    $shipping_zipcode = $_POST['shipping_zipcode']; 
    $shipping_whatsappno = $_POST['shipping_whatsappno']; 
    $billing_whatsappno = $_POST['billing_whatsappno']; 
    $full_name = $_POST['full_name']; 



   


    
    $stmt = $conn->prepare("UPDATE register SET firstname=?, lastname=?,email=?,fullname=?,shipping_state=?,shipping_city=?,shipping_zipcode=?,shipping_whatsappno=?,billing_whatsappno=?
      where id = ? ");
    $stmt->bind_param("ssssssssss", $first_name,$last_name,$email,$full_name,$shipping_state,$shipping_city,$shipping_zipcode,$shipping_whatsappno,$billing_whatsappno,$user_id);  
    $stmt->execute();  
    
     $stmt->close();   

     $response['error'] = false;   
     $response['message'] = 'Your Profile edited successfully';   
             // $response['user'] = ;   

 }  


else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 


//------------------------------------------------------------------------------------------------


//-------------------------------------------------------------------------------------------------
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









