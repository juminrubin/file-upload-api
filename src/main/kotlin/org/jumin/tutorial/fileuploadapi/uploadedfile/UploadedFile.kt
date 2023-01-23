package org.jumin.tutorial.fileuploadapi.uploadedfile

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*


@Entity
@Table(name = "uploaded_files")
class UploadedFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: String? = null
    var name: String? = null
    var type: String? = null

    @Lob
    lateinit var data: ByteArray

    constructor() {}

    constructor(name: String?, type: String?, data: ByteArray) {
        this.name = name
        this.type = type
        this.data = data
    }
}
