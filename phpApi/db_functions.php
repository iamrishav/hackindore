<?php

class DB_Functions {
    private $conn;
    
    function __construct()
    {
        
        require_once 'db_connect.php';
        $db = new DB_Connect();
        $this->conn = $db->connect();
        
    }
    
    
    function __destruct()
    {
        
        
    
    }
    
    function checkExistsUser($phone)
    {
        $stmt = $this->conn->prepare("SELECT * FROM User WHERE Phone=?");
        $stmt->bind_param("s",$phone);
        $stmt->execute();
        $stmt->store_result();
        
        if($stmt->num_rows > 0)
        
        {
            $stmt->close();
            return true;
            
            }
        else 
        {
            
            $stmt->close();
            return false;
        }
        
        
    }
    

public function registerNewUser($phone , $name , $birthdate, $address)
{
    
     $stmt = $this->conn->prepare("INSERT INTO User(Phone , Name , Birthdate , Address) VALUES(?,?,?,?)");
        $stmt->bind_param("ssss",$phone,$name,$birthdate,$address);
        $result=$stmt->execute();
    $stmt->close();
    
    
    if($result)
    {
        $stmt=$this->conn->prepare("SELECT * FROM User WHERE Phone = ?");
        $stmt->bind_param("s",$phone);
        $stmt->execute();
       $user = $stmt->get_result()->fetch_assoc();
        
//         $stmt->bind_result( $data[0], $data[1],$data[2],$data[3]);
// while ($stmt->fetch()) {
//   print $data[0] . ', ' . $data[1] . ', ' . $data[2] . ', ' . $data[3] . ""; 
// }


        $stmt->close();
        return $user;
    
    
        
    }
    
    else 
    return false; 
    
    
}

/*
* Get user information
* return user object if user exists
*return NULL if usr not exists
*/

public function getUserInformation($phone)
{
    
    $stmt = $this->conn->prepare("SELECT *FROM User WHERE Phone = ?");
    $stmt->bind_param("s",$phone);
    
    if($stmt->execute())
    {
        $user = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        
        return $user;
    
    }
    else
    return NULL;
}

/*
* Get Banner
* return list of banner
*/

public function getBanners()
{
    
    $result = $this->conn->query("SELECT *FROM banner ORDER BY ID LIMIT 3");
    
    $banners = array();
    
    while($item = $result->fetch_assoc())
    $banners[] = $item;
    return $banners ;
  
}


/*
* Get Menu
* return list of Menu
*/

public function getMenu()
{
    
    $result = $this->conn->query("SELECT *FROM menu");
    
    $menu = array();
    
    while($item = $result->fetch_assoc())
    $menu[] = $item;
    return $menu ;
  
}

/*
* Load Drinks by menu id
* return list of Drinks
*/

public function getDrinkByMenuID($menuId)
{
    $query = "SELECT *FROM drink WHERE MenuId='".$menuId."'";
    $result = $this->conn->query($query);
    
    $drinks = array();
    
    while($item = $result->fetch_assoc())
    $drinks[] = $item;
    return $drinks ;
  
}

/*
* Get Product Image by drink id
* return list of Images
* By shailendra Mewada
*/

public function getDrinkImageById($drinkId)
{
    
    
    $query = "SELECT *FROM Drinkbanner WHERE DrinkId='".$drinkId."'";
   $result = $this->conn->query($query);
    
    $drinkimage = array();
    
    while($item = $result->fetch_assoc())
    $drinkimage[] = $item;
    return $drinkimage ;
    
    
  
}
    
    /*
* Update Avatar url
* return true or false
*/

public function updateAvatar($phone , $fileName)
{
   return $result = $this->conn->query("UPDATE User SET avatarUrl='$fileName' WHERE Phone='$phone'");
  
}
    
/*
* Get all drinks for search
* return List of drink or empty
*/

public function getAllDrinks()
{
   
    $result = $this->conn->query("SELECT * FROM drink WHERE 1") or die($this->conn->error);
    $drinks = array();
    while($item = $result->fetch_assoc())
    $drinks[]=$item;
    return $drinks;
  
}
    
/*
* Insert new order
* returne true or false
*/

public function insertNewOrder($orderPrice , $orderComment , $orderAddress , $orderDetail , $userPhone)
{
   $stmt = $this->conn->prepare("INSERT INTO `Order`(`OrderDate`, `OrderStatus`, `OrderPrice`, `OrderDetail`, `OrderComment`, `OrderAddress`, `UserPhone`) VALUES (NOW(),0,?,?,?,?,?)")
   or die($this->conn->error);
   $stmt->bind_param("sssss",$orderPrice,$orderDetail,$orderComment,$orderAddress,$userPhone);
   $result = $stmt->execute();
   $stmt->close();
   
   if($result)
            return true;
   else
        return false;
   
  
}
    
/*
* Insert new Menu (Category)
* Server App
*/

public function insertNewCategory($name , $imagePath)
{
   $stmt = $this->conn->prepare("INSERT INTO `menu`(`Name`, `Link`) VALUES (?,?)")
   or die($this->conn->error);
   $stmt->bind_param("ss",$name,$imagePath);
   $result = $stmt->execute();
   $stmt->close();
   
   if($result)
            return true;
   else
        return false;
   
  
}

/*
* UPDATE Menu (Category)
* Return True or false
* Server App
*/

public function updateCategory($id , $name , $imagePath)
{
   $stmt = $this->conn->prepare("UPDATE `menu` SET `Link`=?, `Name`=? WHERE `ID` = ?");
   $stmt->bind_param("sss",$imagePath ,$name, $id);
   $result = $stmt->execute();
   return $result;
   
 
}

/*
* Delete Menu (Category)
* Return True or false
* Server App
*/

public function deleteCategory($id)
{
   $stmt = $this->conn->prepare("DELETE FROM `menu` WHERE `ID` = ?");
   $stmt->bind_param("s", $id);
   $result = $stmt->execute();
   return $result;
   
  
}

/*
* Insert new Drink
* Server App
*/

public function insertNewDrink($name , $imagePath , $price , $menuId)
{
   $stmt = $this->conn->prepare("INSERT INTO `drink`(`Name`, `Link`, `Price`, `MenuId`) VALUES (?,?,?,?)")
   or die($this->conn->error);
   $stmt->bind_param("ssss",$name,$imagePath,$price,$menuId);
   $result = $stmt->execute();
   $stmt->close();
   
   if($result)
            return true;
   else
        return false;
   
  
}


/*
* UPDATE Prouct
* Return True or false
* Server App
*/

public function updateProduct($id , $name , $imagePath , $price , $menuId)
{
   $stmt = $this->conn->prepare("UPDATE `drink` SET `Name`=?,`Link`=?,`Price`=?,`MenuId`=? WHERE `ID` = ?");
   $stmt->bind_param("sssss" ,$name,$imagePath,$price,$menuId, $id);
   $result = $stmt->execute();
   return $result;
   
 
}

/*
* Delete product
* Return True or false
* Server App
*/

public function deleteProduct($id)
{
   $stmt = $this->conn->prepare("DELETE FROM `drink` WHERE `ID` = ?");
   $stmt->bind_param("s", $id);
   $result = $stmt->execute();
   return $result;
   
  
}

/*
* GET all order Base on USerphone or Status
* return list or Null
*/

public function getOrderByStatus($userPhone , $status)
{
    $query = "SELECT *FROM `Order` WHERE `OrderStatus`='" . $status . "' AND `UserPhone` = '" . $userPhone ."'"; 
    
    $result = $this->conn->query($query);
    
    $orders = array();
    
    while($item = $result->fetch_assoc())
    $orders[] = $item;
    return $orders ;
  
}

/*
* GET all order Base on  Status
* return list or Null
* For Server App
*/

public function getOrderServerByStatus($status)
{
    $query = "SELECT *FROM `Order` WHERE `OrderStatus`='" . $status . "'"; 
    
    $result = $this->conn->query($query);
    
    $orders = array();
    
    while($item = $result->fetch_assoc())
    $orders[] = $item;
    return $orders ;
  
}



/*
* INSERT OR UPDATE TOKEN 
* return Token object or FALSE
* For Client & Server
*/

    public function insertToken($phone , $token , $isServerToken)
       {
           $stmt = $this->conn->prepare("INSERT INTO token(phone,token,isServerToken) VALUES(?,?,?) ON DUPLICATE KEY UPDATE token=?,isServerToken=?")
               or die($this->conn->error);
           $stmt->bind_param("sssss",$phone,$token,$isServerToken,$token,$isServerToken);
           $result = $stmt->execute();
           $stmt->close();
           
           //check for successful store
           
           if($result)
           {
               $stmt=$this->conn->prepare("SELECT * FROM token WHERE phone=?");
               $stmt->bind_param("s",$phone);
               $stmt->execute();
               $user = $stmt->get_result()->fetch_assoc();
               $stmt->close();
               return $user;
           }
           else
           {
               return false;
           }
       
       }

/*
* CANCELLED ORDER
* Return True or false
* Client App
*/

     public function cancelOrder($orderId,$userPhone)
     {
          $stmt = $this->conn->prepare("UPDATE `Order` SET `OrderStatus`=-1 WHERE `OrderId`=? AND `UserPhone`=?");
          $stmt->bind_param("ss",$orderId,$userPhone);
          $result = $stmt->execute();
          return $result;
   
 
    }
    
/*
* Get all Notifications from DB
* return List
*/

public function getAllNotifications()
{
   
    $result = $this->conn->query("SELECT * FROM notification ORDER BY ID desc LIMIT 3") or die($this->conn->error);
    $drinks = array();
    while($item = $result->fetch_assoc())
    $drinks[]=$item;
    return $drinks;
  
}
/*
* Insert new Notification
* Server App
*/

public function insertNewNoti($title ,$description)
{
   $stmt = $this->conn->prepare("INSERT INTO `notification`(`Title`, `Description`) VALUES (?,?)")
   or die($this->conn->error);
   $stmt->bind_param("ss",$title ,$description);
   $result = $stmt->execute();
   $stmt->close();
   
   if($result)
            return true;
   else
        return false;
   
  
}

/*
* Get Token
*/

public function getToken($phone ,$isServerToken)
{
   $stmt = $this->conn->prepare("SELECT *FROM `token` WHERE `phone`=? AND `isServerToken`=?")
   or die($this->conn->error);
   $stmt->bind_param("ss",$phone ,$isServerToken);
   $result = $stmt->execute();
   return $result;
   
  
}

}

?>