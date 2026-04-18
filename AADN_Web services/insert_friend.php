<?php
require_once('koneksi.php');

if(isset($_POST['nrp'])){
    $nrp = $_POST['nrp'];

    $checkSql = "SELECT * FROM my_friends WHERE nrp = ?";
    $stmtCheck = $con->prepare($checkSql);
    $stmtCheck->bind_param("s", $nrp);
    $stmtCheck->execute();
    $resultCheck = $stmtCheck->get_result();

    if($resultCheck->num_rows > 0) {
        echo json_encode(array("result" => "ALREADY_EXISTS", "message" => "Sudah menjadi teman"));
    } else {
        $sql = "INSERT INTO my_friends (nrp) VALUES (?)";
        $stmt = $con->prepare($sql);
        $stmt->bind_param("s", $nrp);
        
        if($stmt->execute()){
            $sqlCount = "SELECT COUNT(*) as total FROM my_friends";
            $resCount = $con->query($sqlCount);
            $rowCount = $resCount->fetch_assoc();
            $total = $rowCount['total'];

            echo json_encode(array("result" => "OK", "total" => $total));
        } else {
            echo json_encode(array("result" => "ERROR", "message" => "Gagal menyimpan data"));
        }
    }
} else {
    echo json_encode(array("result" => "ERROR", "message" => "NRP is missing"));
}

$con->close();
?>