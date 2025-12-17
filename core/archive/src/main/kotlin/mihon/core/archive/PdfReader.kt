package mihon.core.archive

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.Closeable

/**
 * Wrapper for reading PDF files using Android's built-in PdfRenderer.
 */
class PdfReader(private val parcelFileDescriptor: ParcelFileDescriptor) : Closeable {

    private val pdfRenderer = PdfRenderer(parcelFileDescriptor)

    val pageCount: Int = pdfRenderer.pageCount

    /**
     * Renders a page to a bitmap.
     */
    fun renderPage(pageIndex: Int, width: Int = 1240, height: Int = 1754): Bitmap {
        val page = pdfRenderer.openPage(pageIndex)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        return bitmap
    }

    fun getMetadata(): Map<String, String> {
        val meta = mutableMapOf<String, String>()
        return meta
    }

    override fun close() {
        pdfRenderer.close()
        parcelFileDescriptor.close()
    }
}
