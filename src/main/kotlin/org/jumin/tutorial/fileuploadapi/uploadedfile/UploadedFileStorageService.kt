package org.jumin.tutorial.fileuploadapi.uploadedfile

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.stream.Stream

@Service
class UploadedFileStorageService {

    @Autowired
    private val repo: UploadedFileRepository? = null

    @Throws(IOException::class)
    fun store(file: MultipartFile): UploadedFile? {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        val uploadedFile = UploadedFile(fileName, file.contentType, file.bytes)
        return repo?.save(uploadedFile)
    }

    fun getUploadedFile(id: String): UploadedFile? {
        return repo?.findById(id)?.get()
    }

    fun getAllUploadedFiles(): Stream<UploadedFile> {
        return repo?.findAll()!!.stream()
    }
}