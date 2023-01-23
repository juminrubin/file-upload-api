package org.jumin.tutorial.fileuploadapi.controller

import org.jumin.tutorial.fileuploadapi.message.ResponseFile
import org.jumin.tutorial.fileuploadapi.message.ResponseMessage
import org.jumin.tutorial.fileuploadapi.uploadedfile.UploadedFile
import org.jumin.tutorial.fileuploadapi.uploadedfile.UploadedFileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.stream.Collectors

@Controller
@CrossOrigin("http://localhost:8081")
class FileUploadController {

    @Autowired
    private val storageService: UploadedFileStorageService? = null

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage?>? {
        return try {
            storageService?.store(file)
            val message = "Uploaded the file successfully: " + file.originalFilename
            ResponseEntity.status(HttpStatus.OK).body<ResponseMessage?>(ResponseMessage(message))
        } catch (e: Exception) {
            val message = "Could not upload the file: " + file.originalFilename + "!"
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body<ResponseMessage?>(ResponseMessage(message))
        }
    }

    @GetMapping("/files")
    fun getListFiles(): ResponseEntity<List<ResponseFile>>? {
        val files: List<ResponseFile> = storageService?.getAllUploadedFiles()!!.map { uploadedFile ->
            val fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(uploadedFile.id!!)
                .toUriString()
            ResponseFile(
                uploadedFile.name!!,
                fileDownloadUri,
                uploadedFile.type!!,
                uploadedFile.data.size.toLong()
            )
        }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body<List<ResponseFile>>(files)
    }

    @GetMapping("/files/{id}")
    fun getFile(@PathVariable id: String): ResponseEntity<ByteArray?>? {
        val uploadedFile: UploadedFile? = storageService?.getUploadedFile(id)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uploadedFile?.name + "\"")
            .body(uploadedFile?.data)
    }
}