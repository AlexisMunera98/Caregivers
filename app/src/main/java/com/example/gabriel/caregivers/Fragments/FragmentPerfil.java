package com.example.gabriel.caregivers.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.caregivers.Activities.MainActivity;
import com.example.gabriel.caregivers.DataBase.DataBaseManager;
import com.example.gabriel.caregivers.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by gabriel on 11/12/2016.
 */


public class FragmentPerfil extends Fragment {
    private TextView edtNombreCuidador, edtApellidoCuidador;
    private ImageButton imgEditPerfil, imgActualizarPerfil, imgEditFotoPerfil;
    private CircleImageView imgPerfil;
    private Button btnCambiarContraseña, btnAceptarCambioContraseña;
    private EditText edtContraseñaActual, edtNuevaContraseña, edtConfirmarContraseña;
    private AlertDialog alertDialog;
    private String mPath;
    private Intent intento;
    private final int MY_PERMISSIONS = 100;
    private static int R_ABRIR_CAMARA = 200;
    private static int SELECT_PICTURE = 300;
    private static String APP_DIRECTORY = "Caregivers/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "Caregivers";

    /**
     * Método constructor
     */
    public FragmentPerfil() {
    }

    /**
     * Método que genera una instacia de esta clase, y asigna los argumentos respectivos.
     *
     * @param arg Datos que se le quieren pasar al fragmento instaciado
     * @return Fragmento creado
     */
    public static FragmentPerfil newInstance(Bundle arg) {
        FragmentPerfil f = new FragmentPerfil();
        if (arg != null) {
            f.setArguments(arg);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        importarComponentes(v);
        declararAccionComponentes();
        String[] datos = ((MainActivity) getActivity()).getDbManager().getDatosCuidador(((MainActivity) getActivity()).getCedula());
        edtNombreCuidador.setText(datos[0]);
        edtApellidoCuidador.setText(datos[1]);

        return v;
    }

    /**
     * Método que define las acciones de los componenetes gráficos.
     */
    private void declararAccionComponentes() {
        edtNombreCuidador.setEnabled(false);
        edtApellidoCuidador.setEnabled(false);
        imgEditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEditPerfil.setVisibility(View.INVISIBLE);
                imgActualizarPerfil.setVisibility(View.VISIBLE);
                edtNombreCuidador.setEnabled(true);
                edtApellidoCuidador.setEnabled(true);
            }
        });
        imgActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEditPerfil.setVisibility(View.VISIBLE);
                imgActualizarPerfil.setVisibility(View.INVISIBLE);
                edtNombreCuidador.setEnabled(false);
                edtApellidoCuidador.setEnabled(false);

                String newNombre = edtNombreCuidador.getText().toString();
                String newApellido = edtApellidoCuidador.getText().toString();
                String cedula = ((MainActivity) getActivity()).getCedula();


                Log.d("Nombre: ", newNombre);
                Log.d("Apellido: ", newApellido);
                Log.d("Cedula: ", cedula);

                ((MainActivity) getActivity()).getDbManager().actualizarNombreApellidoCuidador(newNombre, newApellido, cedula);
                Toast.makeText(getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();


            }
        });
        btnCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.show();
            }
        });
        imgEditFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permisosAceptados()) {
                    crearDialogoEditFoto();
                } else {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }
        });
        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenConGaleria(mPath);

            }
        });

        btnAceptarCambioContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseManager dbManager = ((MainActivity) getActivity()).getDbManager();
                String claveActual = edtContraseñaActual.getText().toString();
                String nuevaClave = edtNuevaContraseña.getText().toString();
                String confirmarClave = edtConfirmarContraseña.getText().toString();
                String cedula = ((MainActivity) getActivity()).getCedula();
                if (claveActual.isEmpty() || nuevaClave.isEmpty() || confirmarClave.isEmpty()) {
                    Toast.makeText(getContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbManager.serLoginCorrecto(cedula, claveActual)) {
                    if (nuevaClave.equals(confirmarClave)) {
                        dbManager.actualizarClaveCuidador(nuevaClave, cedula);
                        Toast.makeText(getContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();

                    } else {
                        Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    /**
     * Método que importa los componentes gráficos desde el xml.
     *
     * @param v Vista del fragmento
     */
    private void importarComponentes(View v) {
        alertDialog = crearDialogoCambiarContraseña();
        edtNombreCuidador = (EditText) v.findViewById(R.id.edtNombreCuidador);
        edtApellidoCuidador = (EditText) v.findViewById(R.id.edtApellidoCuidador);
        imgEditPerfil = (ImageButton) v.findViewById(R.id.imgEditPerfil);
        imgActualizarPerfil = (ImageButton) v.findViewById(R.id.imgActualizarPerfil);
        imgPerfil = (CircleImageView) v.findViewById(R.id.imgPerfil);

        btnCambiarContraseña = (Button) v.findViewById(R.id.btnCambiarContraseña);
        imgEditFotoPerfil = (ImageButton) v.findViewById(R.id.imgEditFotoPerfil);
        if (mPath != null) {
            imgPerfil.setImageBitmap(BitmapFactory.decodeFile(mPath));
        }
    }


    /**
     * Método que crea un dialogo para cambiar la contraseña
     *
     * @return Diálogo para cambiar contraseña
     */
    public AlertDialog crearDialogoCambiarContraseña() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_cambiar_password, null);
        edtContraseñaActual = (EditText) v.findViewById(R.id.edtContraseñaActual);
        edtNuevaContraseña = (EditText) v.findViewById(R.id.edtNuevaContraseña);
        edtConfirmarContraseña = (EditText) v.findViewById(R.id.edtConfirmarContraseña);
        btnAceptarCambioContraseña = (Button) v.findViewById(R.id.btnAceptarCambioContraseña);


        builder.setView(v);
        return builder.create();
    }

    /**
     * Método que crea el dialogo para abrir galeria o camara.
     */
    private void crearDialogoEditFoto() {
        final CharSequence[] option = {"Cámara", "Galería", "Eliminar foto"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Cámara") {
                    abrirCamara();
                } else if (option[which] == "Galería") {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                } else if (option[which] == "Eliminar foto") {
                    imgPerfil.setImageResource(R.drawable.ic_perfil);
                    mPath = "";
                    String cedula = ((MainActivity) getActivity()).getCedula();
                    ((MainActivity) getActivity()).actualizarImagen(mPath);
                    ((MainActivity) getActivity()).getDbManager().insertarFoto(cedula, mPath);

                }
            }
        });
        builder.show();
    }

    /**
     * Metodo que determina si los permisos estan aceptados o no
     *
     * @return Verdadero si los permisos estan aceptados, falso en caso contrario
     */
    public boolean permisosAceptados() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if ((checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(getContext(), CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        return false;
    }

    /**
     * Método que recibe el resultado de la peticion de permisos
     *
     * @param requestCode  codigo de la peticion
     * @param permissions  permisos
     * @param grantResults contiene los permisos.
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permisos Aceptados", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Método que abre la camara del celular
     */
    private void abrirCamara() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();
        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;
            File newFile = new File(mPath);
            intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intento.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intento, R_ABRIR_CAMARA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == R_ABRIR_CAMARA) {
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{mPath}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
            if (mPath.isEmpty()) {
                return;
            }


            int orientacion = getCameraPhotoOrientation(getContext(), Uri.fromFile(new File(mPath)), mPath);
            if (orientacion != 0) {
                rotateBitmap(orientacion);
            }

        }
        if (requestCode == SELECT_PICTURE) {
            Uri uri = data.getData();
            mPath = getRealPathFromURI(getContext(), uri);
            int orientacion = getCameraPhotoOrientation(getContext(), uri, mPath);
            rotateBitmap(orientacion);
        }
    }

    /**
     * Método que obtiene la direccion desde un Uri.
     *
     * @param context    Contexto de la app
     * @param contentUri Uri al que se le va a obtener la direccion
     * @return Direccion obtenida del Uri
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Método que obtiene la orientacion de una imagen
     *
     * @param context   contexto
     * @param imageUri  Uri de la imagen
     * @param imagePath direccion de la imagen
     * @return orientacion en grados de la imagen.
     */
    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    /**
     * Método que abre una imagen para su visualización
     *
     * @param path Dirección de la imagen
     */
    public void abrirImagenConGaleria(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
        startActivity(intent);
    }

    /**
     * Método que rota un bitmap
     *
     * @param x Angulo de rotacióon
     */
    private void rotateBitmap(float x) {
        Bitmap btmp = BitmapFactory.decodeFile(mPath);
        ((MainActivity) getActivity()).getDbManager().insertarFoto(((MainActivity) getActivity()).getCedula(), mPath);
        if (x != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(x);
            btmp = Bitmap.createBitmap(btmp, 0, 0, btmp.getWidth(), btmp.getHeight(), matrix, true);
        }
        imgPerfil.setImageBitmap(btmp);
        ((MainActivity) getActivity()).actualizarImagen(btmp);
    }

    public String getmPath() {
        return mPath;
    }
}