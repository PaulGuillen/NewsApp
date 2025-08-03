package com.devpaul.shared.ui.components.atoms.base.image

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.shared.domain.base64ToImageBitmap
import com.devpaul.shared.domain.bitmapToBase64
import com.devpaul.shared.domain.uriToBitmap
import java.io.File

@Composable
fun ProfileImagePicker(
    defaultImageUrl: String,
    base64Image: String?,
    modifier: Modifier = Modifier,
    showDialogOnClick: Boolean = true,
    isCircular: Boolean = false,
    onImageSelected: (base64: String?, uri: Uri?) -> Unit
) {
    val isInPreview = LocalInspectionMode.current

    if (isInPreview) {
        Box(
            modifier = modifier
                .size(240.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_sentiment_very_dissatisfied_24),
                contentDescription = "Preview User",
                tint = Color.DarkGray,
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
            CameraIconOverlay()
        }
        return
    }

    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    val photoFile = remember {
        File(context.getExternalFilesDir("Pictures"), "profile_${System.currentTimeMillis()}.jpg")
    }
    val photoUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        photoFile
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri.value = photoUri
                val bitmap = uriToBitmap(context, photoUri)
                val base64 = bitmapToBase64(bitmap)
                onImageSelected(base64, photoUri)
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri.value = it
                val bitmap = uriToBitmap(context, it)
                val base64 = bitmapToBase64(bitmap)
                onImageSelected(base64, it)
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                showDialog.value = false
                cameraLauncher.launch(photoUri)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }

    val imageBitmap = remember(base64Image) {
        base64Image?.let { base64ToImageBitmap(it) }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            tonalElevation = 0.dp,
            title = { Text("Selecciona imagen", style = MaterialTheme.typography.titleMedium) },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CameraOrGalleryOption("Cámara", R.drawable.outline_photo_camera_24) {
                        showDialog.value = false
                        if (ContextCompat.checkSelfPermission(
                                context, Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            cameraLauncher.launch(photoUri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }

                    CameraOrGalleryOption("Galería", R.drawable.outline_gallery_thumbnail_24) {
                        showDialog.value = false
                        galleryLauncher.launch("image/*")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    Box(
        modifier = modifier
            .size(240.dp)
            .clickable(enabled = showDialogOnClick) { showDialog.value = true },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(if (isCircular) CircleShape else RectangleShape)
                .background(Color.White)
        ) {
            val imageModifier = Modifier
                .fillMaxSize()
                .clip(if (isCircular) CircleShape else RectangleShape)

            when {
                imageBitmap != null -> {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }

                imageUri.value != null -> {
                    AsyncImage(
                        model = imageUri.value,
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }

                else -> {
                    AsyncImage(
                        model = defaultImageUrl,
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }
            }
        }

        if (showDialogOnClick) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-12).dp, y = (-12).dp)
                    .size(42.dp)
                    .background(BrickRed, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_photo_camera_24),
                    contentDescription = "Cambiar foto",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

}

@Composable
private fun CameraOrGalleryOption(label: String, iconId: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier.size(48.dp).padding(4.dp)
        )
        Text(label)
    }
}

@Composable
private fun BoxScope.CameraIconOverlay() {
    Box(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = (-12).dp, y = (-12).dp)
            .size(42.dp)
            .background(BrickRed, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_photo_camera_24),
            contentDescription = "Cambiar foto",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}
