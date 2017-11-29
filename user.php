<?php

include_once 'db.php';

class User{

	private $db;
	private $db_table = "users";
	
	public function __construct(){
		
		$this->db = new DBConnect();
	}
	
	public function isLoginExist($username, $password){
		
		$query = "SELECT * FROM " . $this->db_table . "WHERE username = '$username' AND password = '$password' LIMIT 1";
		$result = mysqli_query($this->db->getDB(), $query);
		
		if(mysqli_num_rows($result) > 0){
			
			mysqli_close($this->db->getDB());
			return true;
		}
		
		mysqli_close($this->db->getDB());
		return false;
	}
	
	public function createNewRegisterUser($username, $password, $email){
	
		$query = "INSERT INTO users (username, password, email, created_at, 
			updated_at) VALUES ('$username', '$password', '$email', NOW(), NOW())";
			
		$inserted = mysqli_query($this->db->getDB(), $query);
		
		if($inserted == 1)
			$json['success'] = 1;
		else
			$json['success'] = 0;
		
		mysqli_close($this->db->getDB());
		return $json;
	}
	
	public function loginUsers($username, $password){
		
		$json = array();
		$canUserLogin = $this->isLoginExist($username, $password);
		
		if($canUserLogin)
			$json['success'] = 1;
		else
			$json['success'] = 0;
		
		return $json;
	}
	
}
?>
