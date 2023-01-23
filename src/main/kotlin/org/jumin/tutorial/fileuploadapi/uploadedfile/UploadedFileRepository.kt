package org.jumin.tutorial.fileuploadapi.uploadedfile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UploadedFileRepository : JpaRepository<UploadedFile, String?>

