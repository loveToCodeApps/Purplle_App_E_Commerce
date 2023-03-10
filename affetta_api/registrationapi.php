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
        $fullname = $firstname . " " . $middlename . " " . $lastname;  


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
            $stmt = $conn->prepare("INSERT INTO register (firstname,middlename, lastname, email, phone , billing_whatsappno,shipping_whatsappno,fullname) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");  
            $stmt->bind_param("ssssssss",$firstname ,$middlename,$lastname, $email, $phone,$phone,$phone,$fullname);  

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


        $stmt = $conn->prepare("SELECT id, firstname,middlename,lastname, email, phone , address , state , city , zipcode, shipping_whatsappno,billing_whatsappno,shipping_city,shipping_state,shipping_zipcode FROM register WHERE phone = ?");  
        $stmt->bind_param("s",$phone);  
        $stmt->execute();  
        $stmt->store_result(); 

        if($stmt->num_rows > 0){  
            $stmt->bind_result($id, $firstname,$middlename,$lastname, $email, $phone , $address,$city,$state,$zipcode,$shipping_whatsappno,$billing_whatsappno,$shipping_city,$shipping_state,$shipping_zipcode);  
            $stmt->fetch();  
            $user = array(  
                'id'=>$id,   
                'firstname'=>$firstname,  
                'middlename'=>$middlename,  
                'lastname'=>$lastname,   
                'email'=>$email,  
                'phone'=>$phone,
                'address'=>$address ,
                'city'=>$city ,
                'state'=>$state ,
                'zipcode'=>$zipcode,
                'shipping_whatsappno'=>$shipping_whatsappno,
                'billing_whatsappno'=>$billing_whatsappno,
                'shipping_city'=>$shipping_city,
                'shipping_state'=>$shipping_state,
                'shipping_zipcode'=>$shipping_zipcode

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
    $stmt = $conn->prepare("SELECT id, image , url  FROM banners WHERE active='0' ");  
// $stmt->bind_param("s",$id);  
    $stmt->execute();  
    $stmt->store_result(); 

    if($stmt->num_rows > 0){  
        $stmt->bind_result($id,$image,$url);  
        $stmt->fetch();  


        $banner_data = array();

        while($stmt->fetch()){
         $temp = array(); 
         $temp['id'] = $id; 
         $temp['image'] =IMGPATH.$image ;
          $temp['url'] = $url; 
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


    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $heading;
     $temp['url'] =IMGPATH.$url ;
     $temp['img_name'] = $url;


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

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.popular='1' AND (LENGTH(pd.image1) > 0) AND pd.image1 <> '' order by pd.productid DESC limit 7");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



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

case 'featuredAll':  

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory  from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.popular='1' order by rand() limit 20");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



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

$stmt = $conn->prepare("SELECT id, image,url  FROM offerbanners WHERE active='0'");  
//$stmt->bind_param("s",$id);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$image,$url);  
    $stmt->fetch();  



    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['image'] =IMGPATH.$image ;
     $temp['url'] = $url; 

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

$stmt = $conn->prepare("SELECT id, sch_name as heading , image  FROM school_name WHERE active='0' AND (LENGTH(image) > 0) AND image <> '' order by id DESC limit 8");  
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
     $temp['name'] =$url ;
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
case 'brandNamesAll':  

$stmt = $conn->prepare("SELECT id, sch_name FROM school_name WHERE active='0' order by sch_name ASC");  
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
 $response['message'] = 'Brand names Fetch successfull';   
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

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.popular='1' AND (LENGTH(pd.image1) > 0) AND pd.image1 <> '' order by pd.productid DESC limit 7");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }
 
 $response['error'] = false;   
 $response['message'] = 'Hot deals Fetch successfull';   
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

case 'dealsAll':  

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.hide='N' and pd.deals='1' limit 20");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }
 
 $response['error'] = false;   
 $response['message'] = 'All hot deals Fetch successfull';   
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


$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.category='Combo' AND (LENGTH(pd.image1) > 0) AND pd.image1 <> '' order by pd.productid DESC limit 7");  

$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = ' Combo offers Fetch successfull';   
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
case 'comboOffersAll':  


$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory  from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.category='Combo' order by rand() limit 20");  

$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = ' Combo offers Fetch successfull';   
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

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.new_arrival='1' AND (LENGTH(pd.image1) > 0) AND pd.image1 <> '' order by pd.productid DESC limit 7");  
//$stmt->bind_param("s",$id);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
    // print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



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






// //----------------------------------------------------------------------------------------
case 'newArrivalsAll':  

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.new_arrival='1' order by rand() limit 20");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }
 
 $response['error'] = false;   
 $response['message'] = 'New Arrivals Fetch successfull';   
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

case 'getMyCartTotalPrice':  
$id = $_POST['id'];
// $prod_desc_id = $_POST['prod_desc_id'];
// $prod_id = $_POST['prod_id'];

$stmt = $conn->prepare("SELECT SUM(c.total_price) from cart c left JOIN products pd on pd.productid=c.productid LEFT join product_detail_description pdd on pdd.id=c.product_desc_id where c.userid=? and c.status='Added in cart'");  

//, $prod_id,$prod_desc_id
//and pd.productid=? and pdd.id = ?

$stmt->bind_param("s",$id);  
$stmt->execute();        
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($sale);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['sale'] = $sale;
     array_push($banner_data, array_filter($temp));
 }

 $response['error'] = false;   
 $response['message'] = 'Cart total price Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'No Record Found';  
}  
//}  
//}  
break; 

//----------------------------------------------------------------------------------------- 

case 'getMyCart':  
$id = $_POST['id'];
// $prod_desc_id = $_POST['prod_desc_id'];
// $prod_id = $_POST['prod_id'];

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1,c.quantity, pdd.id, pd.disc_type,
 pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from cart c left JOIN products pd on pd.productid=c.productid LEFT join product_detail_description pdd on pdd.id=c.product_desc_id where c.userid=? and c.status='Added in cart'");  

$stmt->bind_param("s",$id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$quantity,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;
     $temp['quantity'] =$quantity ;



     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = ' Combo offers Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
}  
//}  
//}  
break;   


//-------------------------------------------------------------------------------------------- 
case 'getMyWishlist':
$id = $_POST['id'];  

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1,c.quantity, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from wishlist c left JOIN products pd on pd.productid=c.productid LEFT join product_detail_description pdd on pdd.id=c.product_desc_id
    where c.status='Created' and  c.userid=?");  
$stmt->bind_param("s",$id);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$quantity,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['desc_id'] =$desc_id ;



     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = ' wishlist data Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
}  
//}  
//}  
break; 


//----------------------------------------------------------------------------- 
case 'getMyOrders':  
$id = $_POST['id'];

$stmt = $conn->prepare("SELECT invoice_no,total_amount,created_on,status from bill_details where customer_id=?");  


$stmt->bind_param("s",$id);  
$stmt->execute();        
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($order_no,$total_price,$created_on,$status);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['order_no'] = $order_no; 
     $temp['total_price']= $total_price;   
     $temp['created_on'] =$created_on;
     $temp['status'] =$status;


     array_push($banner_data, array_filter($temp));
 }

 $response['error'] = false;   
 $response['message'] = 'My orders Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'No Record Found';  
}  
//}  
//}  
break; 

//-------------------------------------------------------------------------------------

case 'getCurrentOrder':  
$id = $_POST['id'];

$stmt = $conn->prepare("SELECT id,invoice_no,total_amount,created_on,status from bill_details where customer_id=? order by id DESC limit 1");  


$stmt->bind_param("s",$id);  
$stmt->execute();        
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($order_id,$order_no,$total_price,$created_on,$status);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array();
     $temp['order_id'] = $order_id;  
     $temp['order_no'] = $order_no; 
     $temp['total_price']= $total_price;   
     $temp['created_on'] =$created_on;
     $temp['status'] =$status;


     array_push($banner_data, array_filter($temp));
 }

 $response['error'] = false;   
 $response['message'] = 'Current order Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'No Record Found';  
}  
//}  
//}  
break; 

//-------------------------------------------------------------------------------------
case 'addToCart':  
if(isTheseParametersAvailable(array('prod_id','user_id'))){ 
    date_default_timezone_set('Asia/Kolkata'); 
    $prod_id = $_POST['prod_id'];   
    $prod_desc_id = $_POST['prod_desc_id'];   
    $user_id = $_POST['user_id'];
    $unit_price = $_POST['unit_price'];
    $total_price = $_POST['total_price'];
    $number_of_items = $_POST['number_of_items'];
    $confirm_mobile = $_POST['confirm_mobile'];
    $created_on = date('Y-m-d H:i:s');

 $stmt = $conn->prepare("INSERT INTO cart (productid, userid,product_desc_id,unit_price,total_price,quantity,confirm_mobile,ip_address,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");  
    $stmt->bind_param("sssssssss", $prod_id ,$user_id, $prod_desc_id,$unit_price,$total_price,$number_of_items,$confirm_mobile,$_SERVER['REMOTE_ADDR'],$created_on);  

    $stmt->execute();
    $stmt->close();      

$response['error'] = false;   
    $response['message'] = '1 item added to cart successfully!!';   
    $response['user'] = "";


//  $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, 
// $disc_end_date,$category,$subcategory,$conn);

//  print_r($calculation);die(); 

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
    $prod_desc_id = $_POST['prod_desc_id'];   
    $unit_price = $_POST['unit_price'];
    $total_price = $_POST['total_price'];
    $number_of_items = $_POST['number_of_items'];
    $confirm_mobile = $_POST['confirm_mobile'];
// move_uploaded_file($ref_img['tmp_name'], UPLOAD_PATH . $ref_img['name']);
    $stmt = $conn->prepare("INSERT INTO wishlist (productid, userid,product_desc_id,unit_price,total_price,quantity,confirm_mobile) VALUES (?, ?, ?, ?, ?, ?, ?)");  
    $stmt->bind_param("sssssss", $prod_id ,$user_id, $prod_desc_id,$unit_price,$total_price,$number_of_items,$confirm_mobile);  

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
$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.category=?  limit 7");  

$stmt->bind_param("s",$category);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }
 
 $response['error'] = false;   
 $response['message'] = 'particular category products Fetch successfull';   
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
$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.dressmadefor=? order by rand() limit 10");  

$stmt->bind_param("s",$brand);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



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

//------------------------------------------------------------------------------------------------------------------------------


//when we click on any product this page will open up.
case 'getProductDetailDescription':  

//if(isTheseParametersAvailable(array('id'))){  
$id = $_POST['id'];  


$stmt = $conn->prepare("SELECT pd.productid,pd.product_description,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.image1,pd.image2 , pd.image3 , pd.image4 , pd.image5 , pd.image6 ,pd.image7 , pdd.id , pd.bullet_pt1,pd.bullet_pt2,pd.bullet_pt3,pd.bullet_pt4,pd.bullet_pt5,pd.bullet_pt6 , pd.category , pd.disc_type,
 pd.disc_start_date, pd.disc_end_date, pd.subcategory,pd.disc_amt from  products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.productid=? ");  
$stmt->bind_param("s",$id);  
$stmt->execute();
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$disc,$heading,$mrp,$sale, $image1, $image2,$image3,$image4,$image5,$image6,$image7,$desc_id,$bullet_one,$bullet_two,$bullet_three,$bullet_four,$bullet_five,$bullet_six,$category,$disc_type,$disc_start_date,$disc_end_date,$subcategory,$disc_amt);  
    $stmt->fetch();  
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
    $user = array(  
        'id'=>$id,   
        'heading'=>$heading,  
        'sale'=>$calculation['discounted_amount'],  
        'disc'=>strip_tags($disc), 
        'discount'=>round($calculation['discount_perc']),  
        'mrp'=>$calculation['mrp'],  
        'image1'=>IMGPATH.$image1,
        'image2'=>IMGPATH.$image2,
        'image3'=>IMGPATH.$image3,
        'image4'=>IMGPATH.$image4,
        'image5'=>IMGPATH.$image5,
        'image6'=>IMGPATH.$image6,
        'image7'=>IMGPATH.$image7,
        'imageName1'=>$image1,
        'imageName2'=>$image2,
        'imageName3'=>$image3,
        'imageName4'=>$image4,
        'imageName5'=>$image5,
        'imageName6'=>$image6,
        'imageName7'=>$image7,
        'prod_desc_id'=>$desc_id,
        'pt_one'=>strip_tags($bullet_one),
        'pt_two'=>strip_tags($bullet_two),
        'pt_three'=>strip_tags($bullet_three),
        'pt_four'=>strip_tags($bullet_four),
        'pt_five'=>strip_tags($bullet_five),
        'pt_six'=>strip_tags($bullet_six),
        'category'=>$category,
        'gst_type'=>$calculation['gst_type']

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

}  

else
{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 
//------------------------------------------------------------------------------------------------
case 'getSimilarProducts' :  
$category = $_POST['category'];  
$id = $_POST['id'];   

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.category=? AND pd.productid != ? AND (LENGTH(pd.image1) > 0) AND pd.image1 <> '' order by rand() limit 11");  

$stmt->bind_param("ss",$category,$id);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=$calculation['discount_perc'];
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;

     array_push($banner_data, $temp);
 }
 
 $response['error'] = false;   
 $response['message'] = 'Similar category products Fetch successfull';   
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
case 'deleteFromMyWishList':
if(isTheseParametersAvailable(array('id','userid'))){   
    $id = $_POST['id']; 
    $userid = $_POST['userid']; 
    $stmt = $conn->prepare("DELETE from wishlist where status='Created' and productid=? and userid=?");  
    $stmt->bind_param("ss",$id,$userid);  
    $stmt->execute();  
// $stmt->store_result(); 
    $response['error'] = false;   
    $response['message'] = 'Wishlist item deleted successfull';   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'id not passed';  
}  
//}  
//}  
break; 



//-------------------------------------------------------------------------------------
case 'deleteFromMyCart':
if(isTheseParametersAvailable(array('id','userid'))){   
    $id = $_POST['id']; 
    $userid = $_POST['userid']; 
    $stmt = $conn->prepare("DELETE from cart where status='Added in cart' and productid=? and userid=?");  
    $stmt->bind_param("ss",$id,$userid);  
    $stmt->execute();  
// $stmt->store_result(); 
    $response['error'] = false;   
    $response['message'] = 'Cart item deleted successfull';   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'id not passed';  
}  
//}  
//}  
break; 



//-------------------------------------------------------------------------------------
case 'addToCartFromWishlist':  
if(isTheseParametersAvailable(array('prod_id','user_id'))){ 
    date_default_timezone_set('Asia/Kolkata'); 
    $prod_id = $_POST['prod_id'];   
    $prod_desc_id = $_POST['prod_desc_id'];   
    $user_id = $_POST['user_id'];
    $unit_price = $_POST['unit_price'];
    $total_price = $_POST['total_price'];
    $number_of_items = $_POST['number_of_items'];
    $confirm_mobile = $_POST['confirm_mobile'];
    $created_on = date('Y-m-d H:i:s');

    $stmt = $conn->prepare("INSERT INTO cart (productid, userid,product_desc_id,unit_price,total_price,quantity,confirm_mobile,created_on,ip_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");  
    $stmt->bind_param("sssssssss", $prod_id ,$user_id, $prod_desc_id,$unit_price,$total_price,$number_of_items,$confirm_mobile,$created_on,$_SERVER['REMOTE_ADDR']);  

    if($stmt->execute())
    {
       $stmt_status_change = $conn->prepare("UPDATE wishlist SET status='Added in cart' where userid=? and productid=?"); 
       $stmt_status_change->bind_param("ss",$user_id, $prod_id);  

       $stmt_status_change->execute();
       $stmt->close();  
   }

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

case 'updateMyCartQuantity':  
if(isTheseParametersAvailable(array('prod_id','user_id'))){  
    $prod_id = $_POST['prod_id'];   
    $user_id = $_POST['user_id'];
    $unit_price = $_POST['unit_price'];
    $number_of_items = $_POST['number_of_items'];
    $total_price =$unit_price*$number_of_items;
    $stmt = $conn->prepare("UPDATE cart SET quantity=? , total_price=?  where productid=? and userid=?");  
    $stmt->bind_param("ssss", $number_of_items,$total_price,$prod_id,$user_id);  

    $stmt->execute();

    $response['error'] = false;   
    $response['message'] = 'Cart updated successfully';   
}  


else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 

//-----------------------------------------------------------------------------------
case 'getHomeSearchedProducts' :  
$category = $_POST['category']; 
$word = "%$category%";  
$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1, pdd.id, pd.disc_type, pd.disc_start_date, pd.disc_end_date,pd.category, pd.subcategory from products pd left join product_detail_description pdd on pd.productid=pdd.product_id where pd.category LIKE ? OR pd.subcategory LIKE ? OR pd.child_category LIKE ? order by rand() limit 20");  

$stmt->bind_param("sss",$word,$word,$word);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$mrp,$sale,$disc_amt,$image,$desc_id, $disc_type,$disc_start_date,$disc_end_date,$category,$subcategory);  
    $stmt->fetch();

    $banner_data = array();

    while($stmt->fetch()){
     $calculation = getpro_calculation($id, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn);
     //print_r($calculation);die();

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['heading']= $name;
     $temp['sale'] =$calculation['discounted_amount'];
     $temp['disc']=round($calculation['discount_perc']);
     $temp['mrp']=$calculation['mrp'];
     $temp['image'] =IMGPATH.$image ;
     $temp['name'] =$image ;



     array_push($banner_data, $temp);
 }
 
 $response['error'] = false;   
 $response['message'] = 'Searched products Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'searched products are not available';  
}  
//}  
//}  
break; 

//-------------------------------------------------------------
case 'getCategoryNamesAll':  

$stmt = $conn->prepare("SELECT category_name FROM categories UNION SELECT subcategory_name FROM subcategories  UNION SELECT child_category FROM child_categories");  
//$stmt->bind_param("s",$id);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($category);  
    $stmt->fetch();  


    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['category'] = $category; 
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'All categories Fetch successfull';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id';  
}  
//}  
//}  
break;  

// //-------------------------------------------

case 'deleteAllMyCart':
if(isTheseParametersAvailable(array('userid'))){   
    $userid = $_POST['userid']; 
    $stmt = $conn->prepare(" DELETE from cart where status='Added in cart' and userid=? ");  
    $stmt->bind_param("s",$userid);  
    $stmt->execute();  
// $stmt->store_result(); 
    $response['error'] = false;   
    $response['message'] = 'Cleared your cart successfully';   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'id not passed';  
}  
//}  
//}  
break; 



//-------------------------------------------------------------------------------------
case 'deleteAllFromMyWishList':
if(isTheseParametersAvailable(array('userid'))){   
    $userid = $_POST['userid']; 
    $stmt = $conn->prepare("DELETE from wishlist where status='Created' and userid=?");  
    $stmt->bind_param("s",$userid);  
    $stmt->execute();  
// $stmt->store_result(); 
    $response['error'] = false;   
    $response['message'] = 'Cleared your wishlist successfully!!';   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'id not passed';  
}  
//}  
//}  
break; 



//-------------------------------------------------------------------------------------

case 'editMyAddress':  

if(isTheseParametersAvailable(array('shipping_city','shipping_zipcode','shipping_state','address','user_id'))){ 

    $user_id = $_POST['user_id']; 
    $shipping_state = $_POST['shipping_state']; 
    $shipping_city = $_POST['shipping_city']; 
    $shipping_zipcode = $_POST['shipping_zipcode']; 
    $address = $_POST['address']; 


    $stmt = $conn->prepare("UPDATE register SET address=?, state=?,city=?,zipcode=?,shipping_state=?,shipping_city=?,shipping_zipcode=?,shipping_address=? 
      where id = ? ");
    $stmt->bind_param("sssssssss", $address,$shipping_state,$shipping_city,$shipping_zipcode,$shipping_state,$shipping_city,$shipping_zipcode,$address,$user_id);  
    $stmt->execute();  

    $stmt->close();   

    $response['error'] = false;   
    $response['message'] = 'Added shipping address successfully';   

}  

else
{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 
//------------------------------------------------------------------------------------------------
case 'cartItemsCount':  
$id = $_POST['id'];
$stmt = $conn->prepare("SELECT COUNT(userid) FROM cart where userid = ? and status='Added in cart' ");
$stmt->bind_param("s",$id);  
$stmt->execute();  

$stmt->bind_result($count);  
$stmt->fetch();  
if($count>=0){
    $response['error'] = false;   
    $response['message'] = 'cart items count Fetch successfull';   
    $response['count'] = $count;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Record not found';  
}  
//}  
break;   


//-----------------------------------------------------------------------------------
case 'confirmOrder':  

if(isTheseParametersAvailable(array('id'))){ 
    $id = $_POST['id'];    
    $stmt = $conn->prepare("UPDATE cart SET status='ordered'
      where userid = ? AND status='Added in cart'");
    $stmt->bind_param("s", $id);  
    $stmt->execute();  

    $stmt->close();   

    $response['error'] = false;   
    $response['message'] = 'Order confirmed successfully';   

}  

else
{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 
//------------------------------------------------------------------------------------------------
case 'insertBillDetails':
if(isTheseParametersAvailable(array('email_id'))){  
    date_default_timezone_set('Asia/Kolkata'); 
    $total_amount = $_POST['total'];    
    $customer_name = $_POST['name']; 
    $customer_address = $_POST['address'];   
    $city = $_POST['city'];   
    $state = $_POST['state'];     
    $pincode = $_POST['pincode'];     
    $contact_no = $_POST['contact'];   
    $email_id = $_POST['email_id'];     
    $customer_id = $_POST['id'];
    $loop_counter = $_POST['quantity'];
    $country = 'India';
    $status='Order Received';
    $created_on = date('Y-m-d H:i:s');
    $user_ip = $_SERVER['REMOTE_ADDR'];
    $company_name = "Affetta";
    $remark = $product_description = "";
 
    $order_id = '1';

    $stmt_new = $conn->prepare("SELECT max(id) lastid from bill_details");  
    $stmt_new->execute();
    $stmt_new->store_result(); 

    if($stmt_new->num_rows > 0){  
        $stmt_new->bind_result($lastid);  

        while($stmt_new->fetch() ){
          $order_id = $lastid+1;
      }
  }


  $invoice_no = $order_id.'_'.rand(1000000000,9999999999); 

  $stmt = $conn->prepare("INSERT INTO bill_details (invoice_no,total_amount,customer_name,customer_address,city,country,state,pincode,contact_no,email_id,customer_id,status,shipping_address,shipping_fullname,shipping_state,shipping_city,shipping_country,shipping_zipcode,created_on,billing_whatsappno,shipping_whatsappno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");  
  $stmt->bind_param("sssssssssssssssssssss", $invoice_no , $total_amount, $customer_name , $customer_address , $city , $country , $state , $pincode , $contact_no, $email_id,$customer_id,$status,$customer_address,$customer_name,$state,$city,$country,$pincode,$created_on,$contact_no,$contact_no);  

  if($stmt->execute()){
     $stmt_cart = $conn->prepare("SELECT pp.item_name, pdd.sku_code,pdd.sale_price,cc.productid,cc.product_desc_id,cc.cgst,cc.sgst,cc.gst_percentage,cc.gst_type,cc.quantity,cc.total_price,cc.prunitwithoutgst,cc.final_amount,cc.cart_id as cartid,cc.disc_type,cc.disc_amt,cc.total_discount from cart cc left join products pp on cc.productid=pp.productid left join product_detail_description pdd on pp.productid=pdd.product_id where userid=? and status='Added in cart'");  
      $stmt_cart->bind_param("s", $customer_id);  
     $stmt_cart->execute();
     $stmt_cart->store_result(); 
     if($stmt_cart->num_rows > 0){  
        $stmt_cart->bind_result($product_name, $sku_code,$sale_price,$product_id,$product_desc_id,$cgst,$sgst,$gst_percentage,$gst_type,$quantity,$total_price,$prunitwithoutgst,$final_amount,$cartid, $disc_type,$disc_amt,$total_discount);

        while($stmt_cart->fetch()){
        $suborder_id=$invoice_no.'-'.getRandomString(5);

        $stmt_ecom = $conn->prepare("INSERT INTO ecommerce_orders (order_id,suborder_id,order_date,company_name,sku_code,product_name,sale_price,customer_name,customer_address,city,country,state,pincode,contact_no,email_id,remark,created_by,created_on,customer_id,product_description,product_id,product_desc_id,cgst,sgst,gst_percentage,gst_type,user_ip,qty,total_price,prunitwithoutgst,final_amount,cartid, disc_type, disc_amt,total_discount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");  
        $stmt_ecom->bind_param("sssssssssssssssssssssssssssssssssss", $invoice_no , $suborder_id, $created_on, $company_name ,$sku_code, $product_name,$sale_price,$customer_name,$customer_address, $city, $country, $state, $pincode,$contact_no,$email_id,$remark,$customer_id,$created_on,$customer_id,$product_description,$product_id,$product_desc_id,$cgst,$sgst,$gst_percentage,$gst_type,$user_ip,$quantity,$total_price,$prunitwithoutgst,$final_amount, $cartid,$disc_type,$disc_amt,$total_discount); 
         if($stmt_ecom->execute()){
             $stmt_cart1 = $conn->prepare("UPDATE cart set status='ordered' where cart_id=?");  
              $stmt_cart1->bind_param("s", $cartid);  
             $stmt_cart1->execute();
         }
        }
      }
  }  
  $stmt->close();  

  $response['error'] = false;   
  $response['message'] = 'Order placed successfully';   
  $response['user'] = "";   

}  


else{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 
//---------------------------------------------------------------------------------------------------------------------------------
case 'getMyShipmentData':  
$id = $_POST['id'];
// $prod_desc_id = $_POST['prod_desc_id'];
// $prod_id = $_POST['prod_id'];

$stmt = $conn->prepare("SELECT pd.productid,pd.item_name, pdd.mrp_price,pdd.sale_price ,pd.disc_amt,pd.image1,c.qty from ecommerce_orders c left JOIN products pd on pd.productid=c.product_id LEFT join product_detail_description pdd on pdd.id=c.product_desc_id where c.order_id=?");  

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


     array_push($banner_data, array_filter($temp));
 }

 $response['error'] = false;   
 $response['message'] = 'Shipment details Fetch successfull';   
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


case 'updateProfilePicture':  

if(isTheseParametersAvailable(array('id','picture'))){ 
    $id = $_POST['id'];    
    $picture = $_POST['picture']; 
    $file_path = "profile/" . $id . ".jpg";
    file_put_contents($file_path, base64_decode($picture));


    $stmt = $conn->prepare("UPDATE register SET picture=? where id = ? ");
    $stmt->bind_param("ss", $file_path,$id);  
    $stmt->execute();  

    $stmt->close();   

    $response['error'] = false;   
    $response['message'] = 'Your Profile picture updated successfully';   

}  

else
{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 
//------------------------------------------------------------------------------------------------
 case 'getProfilepicture':  

    if(isTheseParametersAvailable(array('id'))){  
        $id = $_POST['id'];  

        $stmt = $conn->prepare("SELECT picture FROM register WHERE id = ?");  
        $stmt->bind_param("s",$id);  
        $stmt->execute();  
        $stmt->store_result(); 

        if($stmt->num_rows > 0){  
            $stmt->bind_result($picture);  
            $stmt->fetch();  
            $user = array(  
                'picture'=>UPLOADPATH.$picture  
            );  

            $response['error'] = false;   
            $response['message'] = 'Picture got successful !!';   
            $response['user'] = $user;   
        }  
        else{  
            $response['error'] = false;   
            $response['message'] = 'something wrong happened!';  
        }  
    }  
    break; 
//-----------------------------------------------------------------------------------------------------


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


function getRandomString($length = 8) {
    $characters = md5(uniqid(rand(), true));
    $string = '';

    for ($i = 0; $i < $length; $i++) {
        $string .= $characters[mt_rand(0, strlen($characters) - 1)];
    }

    return $string;
}

function getproductdescription($productid,$conn)
{

    $stmt = $conn->prepare("SELECT * FROM product_detail_description WHERE product_id = ?");  
    $stmt->bind_param("s",$productid);  
    $stmt->execute();  
    //$resultSet = $stmt->fetch();
    $result = $stmt->get_result();
    return $result->fetch_assoc();
}

function getproductdescription1($product_desc_id,$conn)
{

    $stmt = $conn->prepare("SELECT * FROM product_detail_description WHERE id = ?");  
    $stmt->bind_param("s",$product_desc_id);  
    $stmt->execute(); 
   $result = $stmt->get_result(); // get the mysqli result
   // $resultSet =[];
   //  while ($row = $result->fetch_assoc()) {
   //      $resultSet[] = $row;
   //  }
    return $result->fetch_assoc();
}

function getprices_sum($product_id,$desc_id,$conn)
{
   
    $stmt = $conn->prepare("SELECT id,sale_price, mrp_price, qty totalqty, gst_type FROM product_detail_description WHERE product_id = ? and id=?");  
    $stmt->bind_param("ss",$product_id, $desc_id);  
    $stmt->execute();  
    $result = $stmt->get_result();
    return $result->fetch_assoc();
}

function get_total_productstock($productid,$sku,$conn)
{
    $stmt = $conn->prepare("SELECT stock_count as totalqty from product_stock where product_id=? and skucode=?");  
    $stmt->bind_param("ss",$productid, $sku);  
    $stmt->execute();  
   $result = $stmt->get_result();
    return $result->fetch_assoc();
}

function getcategory_byname($cat_name,$conn){
    $stmt = $conn->prepare("select * from categories where category_name=? and active='0'");  
    $stmt->bind_param("s",$cat_name);  
    $stmt->execute();  
    $result = $stmt->get_result();
    return $result->fetch_assoc();
} 

function getsubcategory_byname($subcat_name,$conn){
    $stmt = $conn->prepare("select * from subcategories where subcategory_name like '%".$subcat_name."%' and active='0'");  
    $stmt->execute();  
    $result = $stmt->get_result();
    return $result->fetch_assoc();
}

function getgstrules($category,$subcategory,$conn){
    $stmt = $conn->prepare("select * from gst_rules where category_id =?");  
    $stmt->bind_param("s",$category);  
    $stmt->execute();  
   $result = $stmt->get_result();
    return $result->fetch_assoc();
}

function getpro_calculation($productid, $desc_id, $disc_amt, $disc_type, $disc_start_date, $disc_end_date,$category,$subcategory,$conn)
{
    $discount=0;
    if($desc_id==0){
        $desc_detail = getproductdescription($productid,$conn);
        $desc_id = $desc_detail['id'];
    }else{
        $desc_detail = getproductdescription1($desc_id,$conn);
    }
   
    $prices = getprices_sum($productid,$desc_id,$conn);

    $qty1 = get_total_productstock($productid, $desc_detail['sku_code'],$conn);
    if(empty($qty1)){
        $qty1['totalqty']=0;
    }

    if($prices['mrp_price'] > 0){
        $diff = $prices['mrp_price'] - $prices['sale_price'];
        $discount = ($diff/$prices['mrp_price'])*100; 
    }else{ $discount = 0; }
    $discounted_amount=round($prices['sale_price']);
    $og_price = round($prices['mrp_price']);
    $og_sale_price = round($prices['sale_price']);

    $special_disc=0;

    if($disc_amt!=0 && $disc_amt!=''){
      $from_date = date('Y-m-d', strtotime($disc_start_date));
      $to_date = date('Y-m-d', strtotime($disc_end_date));
      $cur_date = date('Y-m-d');
      if($cur_date >= $from_date && $cur_date <= $to_date){
        $disc_type = $disc_type;
        $disc_amt = $disc_amt;
        if($disc_type != '' && $disc_amt!=0){
          if($disc_type=="Percentage"){
              $special_disc = round($prices['sale_price']) * $disc_amt/100;
              $discount = $disc_amt;
          }else{
              $special_disc = $disc_amt;
          }

          $discounted_amount = round($prices['sale_price']) - $special_disc;
      }
    }else{
    $discounted_amount = round($prices['sale_price']);
    }
}

if($special_disc==0){
    $og_price = round($prices['mrp_price']);
}
$cat_detail = getcategory_byname(trim($category),$conn);

$subcat_detail = getsubcategory_byname(trim($subcategory),$conn);

if(!empty($cat_detail) && !empty($subcat_detail)){
    $gst_rules = getgstrules($cat_detail['id'], $subcat_detail['id'],$conn);
    //print_r($gst_rules);die();
}elseif(!empty($cat_detail)){
    $gst_rules = getgstrules($cat_detail['id'], '',$conn);
}else{
    $gst_rules = array('min_range'=>0, 'mingst_perc'=>0, 'maxgst_perc'=>0);
}

$amt_before_gst = $discounted_amount;
$amt_after_gst = $discounted_amount;


if(!empty($gst_rules)){
    if($discounted_amount <= $gst_rules['min_range']){
        $listprice_gst_percentage = trim($gst_rules['mingst_perc']);
    }else{
        $listprice_gst_percentage = trim($gst_rules['maxgst_perc']);
    }
    if($og_price <= $gst_rules['min_range']){
        $mrp_gst_percentage = trim($gst_rules['mingst_perc']);
    }else{
        $mrp_gst_percentage = trim($gst_rules['maxgst_perc']);
    }
    if($og_sale_price <= $gst_rules['min_range']){
        $saleprice_gst_percentage = trim($gst_rules['mingst_perc']);
    }else{
        $saleprice_gst_percentage = trim($gst_rules['maxgst_perc']);
    }
}else{
    $listprice_gst_percentage=$mrp_gst_percentage=0;$saleprice_gst_percentage=0;
}


if($prices['gst_type'] == "inc"){
    $listpricegst = (($discounted_amount * $listprice_gst_percentage) / (100+$listprice_gst_percentage))*1;
    $salepricegst = (($og_sale_price * $saleprice_gst_percentage) / (100+$saleprice_gst_percentage))*1;
    $mrpgst = (($og_price * $mrp_gst_percentage) / (100+$mrp_gst_percentage))*1;

    $discounted_amount = round(($discounted_amount*1));
    $amt_before_gst = round(($discounted_amount*1)-$listpricegst);
    $amt_after_gst = $discounted_amount;
    $og_price = round(($og_price));
   // print_r($discounted_amount);die();
    $og_sale_price = round(($og_sale_price*1) - $salepricegst);
}else if($prices['gst_type'] == "exc"){
    $listpricegst = ($discounted_amount * ($listprice_gst_percentage/100))*1;
    $mrpgst = ($og_price * ($mrp_gst_percentage/100))*1;
    $salepricegst = ($og_sale_price * ($saleprice_gst_percentage/100))*1;
    $amt_before_gst = round(($discounted_amount*1));

    $discounted_amount = round($discounted_amount+$listpricegst);

    $amt_after_gst = round($discounted_amount+$listpricegst);
    $og_price = round($og_price+$mrpgst);
    $og_sale_price = round($og_sale_price+$salepricegst);
}else{
    $listpricegst = $mrpgst = $salepricegst = 0;
}

$result['salegst']=round($listpricegst,2);
$result['mrpgst']=round($mrpgst,2);
$result['discount_perc']=$discount;
//$result['og_mrp']=$og_price;
//$result['og_sale_price']=$og_sale_price;
$result['mrp']=round($prices['mrp_price']);
$result['sale_price']=round($prices['sale_price']);
$result['gst_percentage'] = $listprice_gst_percentage;
$result['discounted_amount']=$discounted_amount;
$result['amt_before_gst']=$amt_before_gst;
$result['amt_after_gst']=$amt_after_gst;
$result['gst_type'] = $prices['gst_type'];
$result['totalqty'] = $qty1['totalqty'];
$result['desc_id'] = $prices['id'];
return $result;
} 
?>  