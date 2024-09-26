import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';

class TrainingPage extends StatefulWidget {
  const TrainingPage({super.key});

  @override
  State<TrainingPage> createState() => _TrainingPageState();
}

class _TrainingPageState extends State<TrainingPage> {
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
                  child:
                      const Text('Cadastrar Treinamento', style: TextStyle(fontSize: 20))),
              const SizedBox(height: 20),
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
                  child: const Text('Consultar Treinamentos',
                      style: TextStyle(fontSize: 20))),
                      const SizedBox(height: 20),
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
                  child: const Text('Consultar Treinamentos Realizados',
                      style: TextStyle(fontSize: 20))),
            ],
          ),
        ),
      ),
    );
  }
}
