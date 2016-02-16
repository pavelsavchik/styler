package com.satch.service.file

import com.satch.domain.Supplier
import grails.transaction.Transactional

@Transactional
class FileManagerService {

    static final String WORKAREA_DIR = "workarea"

    String getWorkFolderForCurrentUser() {
        def supplierId = 'adidas' //TODO get from current user
        def workDir = new File(workareaFile.path + File.separator + supplierId)
        if(!workDir.exists()){
            workDir.mkdir()
        }
        return workDir.path
    }

    File getWorkFolderForSupplier(Supplier supplier) {
        def supplierId = supplier.supplierId
        def workDir = new File(workareaFile.path + File.separator + supplierId)
        if(!workDir.exists()){
            workDir.mkdir()
        }
        return workDir
    }

    File getWorkareaFile(){
        File catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile()
        File workareaDir = new File( catalinaBase, "webapps/workarea" )
        if(!workareaDir.exists()){
            workareaDir.mkdir()
        }
        return workareaDir;
    }

    String getFilePath(File file){
        return (file.path - workareaFile.path - File.separator).replaceAll('\\\\', '/')
    }

    File getFile(String path){
        File file = new File(workareaFile.path + File.separator + path)
        return file.exists() ? file : null
    }

    File copyFileToSupplierDir(File file, Supplier supplier, String relativePath = null){
        InputStream inStream = null
        OutputStream outStream = null
        File destDir = null
        File destFile = null
        try{
            destDir =new File(getWorkFolderForSupplier(supplier).path + File.separator + (relativePath ?: "") + File.separator)
            if(!destDir.exists()){
                destDir.mkdirs()
            }
            destFile = new File(destDir.path + File.separator + file.name)

            inStream = new FileInputStream(file)
            outStream = new FileOutputStream(destFile)

            byte[] buffer = new byte[1024]

            int length
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length)
            }

            inStream.close()
            outStream.close()

        }catch(IOException e){
            e.printStackTrace()
        }
        return destFile
    }
}
