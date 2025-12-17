package mihon.core.archive

import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import com.shockwave.pdfium.PdfiumCore
import com.shockwave.pdfium.util.Size
import java.io.Closeable

/**
 * Wrapper for reading PDF files using PdfiumAndroid.
 */
class PdfReader(private val parcelFileDescriptor: ParcelFileDescriptor) : Closeable {

    private val pdfiumCore = PdfiumCore()
    private val pdfDocument = pdfiumCore.newDocument(parcelFileDescriptor, "")

    val pageCount: Int = pdfiumCore.getPageCount(pdfDocument)

    /**
     * Renders a page to a bitmap.
     */
    fun renderPage(pageIndex: Int, width: Int = 1240, height: Int = 1754): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageIndex, 0, 0, width, height)
        return bitmap
    }

    /**
     * Gets the size of a page.
     */
    fun getPageSize(pageIndex: Int): Size {
        return pdfiumCore.getPageSize(pdfDocument, pageIndex)
    }

    fun getMetadata(): Map<String, String> {
        val meta = mutableMapOf<String, String>()
        return meta
    }

    override fun close() {
        pdfiumCore.closeDocument(pdfDocument)
        parcelFileDescriptor.close()
    }
}
