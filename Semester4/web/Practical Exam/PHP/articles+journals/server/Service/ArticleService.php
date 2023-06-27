<?php

require_once '../Repository/ArticlesRepository.php';
require_once '../Repository/JournalRepository.php';

class ArticleService
{
    private $repository;
    private $jrepo;

    public function __construct()
    {
        $this->repository = new ArticlesRepository();
        $this->jrepo = new JournalRepository();
    }

    public function getAll($user, $journalId): array
    {
        return $this->repository->getAll($user, $journalId);
    }

    public function getAllW(): array
    {
        return $this->repository->getAllW();
    }

    public function add($user, $journalName, $summary): void
    {
        $journal = $this->jrepo->getByName($journalName);

        if ($journal == null)
        {
            $this->jrepo->add(new Journal(null, $journalName));
            $journal = $this->jrepo->getByName($journalName);
        }

        $this->repository->add($user, $journal->id, $summary);
    }

    public function delete($id): void
    {
        $this->repository->delete($id);
    }

    public function login($user)
    {
        return $this->repository->loginUser($user);
    }
}
?>