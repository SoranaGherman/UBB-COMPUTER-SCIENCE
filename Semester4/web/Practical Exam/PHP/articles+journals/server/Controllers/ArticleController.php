<?php

require_once '../Service/ArticleService.php';

class ArticleController 
{
    private $service;

    public function __construct()
    {
        $this->service = new ArticleService();
    }
    
    public function handleRequest()
    {
        if (!isset($_SERVER["REQUEST_METHOD"]))
        {
            return;
        }

        $method = $_SERVER["REQUEST_METHOD"];

        if ($method === "GET")
        {
            $this->handleGet();
            return;
        }

        if ($method === "POST")
        {
            $this->handlePost();
            return;
        }

        if ($method === "DELETE")
        {
            $this->handleDelete();
            return;
        }
    }

    private function handleDelete()
    {
        if (!isset($_GET["id"]))
        {
            echo "Invalid input";
            return;
        }

        $id = $_GET["id"];
        $this->service->delete($id);
    }

    private function handlePost()
    {
        if (isset($_GET['login']))
        {
            echo $this->service->login($_GET['login']);
            return;
        }


        $data = json_decode(file_get_contents("php://input"), true);
        if (!$this->validateInput($data, ["user", "journalName", "summary"]))
        {
            echo "Invalid input";
            return;
        }

        $this->service->add($data["user"], $data["journalName"], $data["summary"]);
    }

    private function handleGet()
    {
        if ($this->validateInput($_GET, ["user", "journalId"]))
        {
            $books = $this->service->getAll($_GET["user"], $_GET["journalId"]);
            echo json_encode($books);
            return;
        }

        $books = $this->service->getAllW();
        echo json_encode($books);
    }

    private function validateInput($data, $fields) 
    {
        for ($i = 0; $i < count($fields); $i++)
        {
            if (!isset($data[$fields[$i]]))
            {
                return false;
            }
        }

        return true;
    }
}

$controller = new ArticleController();
$controller->handleRequest();
?>