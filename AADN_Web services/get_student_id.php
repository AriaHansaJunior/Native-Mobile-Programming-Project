<?php
require_once('koneksi.php');

if(isset($_POST['nrp'])){
    $nrp = $_POST['nrp'];

    $sql = "SELECT * FROM student WHERE nrp = ?";
    $stmt = $con->prepare($sql);
    $stmt->bind_param("s", $nrp);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $data = $result->fetch_assoc();

        $sqlCheck = "SELECT * FROM my_friends WHERE nrp = ?";
        $stmtCheck = $con->prepare($sqlCheck);
        $stmtCheck->bind_param("s", $nrp);
        $stmtCheck->execute();
        $resCheck = $stmtCheck->get_result();

        if($resCheck->num_rows > 0) {
            $data['is_friend'] = true; 
        } else {
            $data['is_friend'] = false; 
        }

        echo json_encode(array("result" => "OK", "data" => $data));
    } else {
        echo json_encode(array("result" => "ERROR", "message" => "Student not found"));
    }
} else {
    echo json_encode(array("result" => "ERROR", "message" => "NRP is missing"));
}

$con->close();
?>