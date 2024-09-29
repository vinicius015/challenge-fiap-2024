import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';

class UpdateEmployeesDataPage extends StatefulWidget {
  const UpdateEmployeesDataPage({super.key});

  @override
  State<UpdateEmployeesDataPage> createState() =>
      _UpdateEmployeesDataPageState();
}

class _UpdateEmployeesDataPageState extends State<UpdateEmployeesDataPage> {
  void _uploadFile() async {
    // Usando o FilePicker para selecionar um arquivo
    FilePickerResult? result = await FilePicker.platform.pickFiles(
      type: FileType.any,
      allowMultiple: false,

      //allowedExtensions: ['xlsx'],
    );

    if (result != null) {
      //   File file = File(result.files.single.path!);

      //   var bytes = file.readAsBytesSync();
      //   var excel = Excel.decodeBytes(bytes);

      //   for (var table in excel.tables.keys) {
      //     print('Table: $table'); // Nome da tabela
      //     for (var row in excel.tables[table]!.rows) {
      //       for (var cell in row) {
      //       }
      //     }
      //   }

      // } else {
      //   print("Nenhum arquivo selecionado");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(height: 25),
              ElevatedButton(
                onPressed: () {
                  _uploadFile();
                  
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                        content: Text('Base de dados atualizada com sucesso!')),
                  );
                },
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(400, 60),
                  backgroundColor: euronSoftPurple,
                  elevation: 0,
                  foregroundColor: euronWhite,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(4.0),
                  ),
                ),
                child: const Text(
                  'Upload Arquivo',
                  style: TextStyle(fontSize: 20),
                ),
              ),
              const SizedBox(height: 15),
              const Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    'Tamanho máximo: ',
                    style: TextStyle(
                      fontSize: 16,
                      color: Color.fromARGB(221, 61, 61, 61),
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    '1 GB',
                    style: TextStyle(fontSize: 16, color: Colors.grey),
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
