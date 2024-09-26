import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';

class UpdateEmployeesDataPage extends StatefulWidget {
  const UpdateEmployeesDataPage({super.key});

  @override
  State<UpdateEmployeesDataPage> createState() =>
      _UpdateEmployeesDataPageState();
}

class _UpdateEmployeesDataPageState extends State<UpdateEmployeesDataPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(imagePath: 'assets/euron_logo.png'),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(height: 25),
              ElevatedButton(
                  onPressed: () => {},
                  style: ElevatedButton.styleFrom(
                      minimumSize: const Size(400, 60),
                      backgroundColor: euronSoftPurple,
                      elevation: 0,
                      foregroundColor: euronWhite,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(4.0),
                      )),
                  child: const Text('Upload Arquivo',
                      style: TextStyle(fontSize: 20))),
              const SizedBox(height: 15),
              const Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('Tamanho máximo: ',
                      style: TextStyle(
                          fontSize: 16,
                          color: Color.fromARGB(221, 61, 61, 61),
                          fontWeight: FontWeight.bold)),
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
