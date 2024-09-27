import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';

import '../../config/globals.dart';

class HomePage extends StatelessWidget {
  final bool isAdmin;
  final VoidCallback navigateToTraining;
  final VoidCallback? navigateToUpdateEmployeesData;

  const HomePage(
      {super.key,
      required this.isAdmin,
      required this.navigateToTraining,
      this.navigateToUpdateEmployeesData});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Text(
              'Bem-vindo(a) ao Euron Management Portal',
              style: TextStyle(fontSize: 23),
            ),
            const SizedBox(height: 25),
            ElevatedButton(
                onPressed: navigateToTraining,
                style: ElevatedButton.styleFrom(
                    minimumSize: const Size(400, 60),
                    backgroundColor: euronSoftPurple,
                    elevation: 0,
                    foregroundColor: euronWhite,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(4.0),
                    )),
                child:
                    const Text('Treinamentos', style: TextStyle(fontSize: 20))),
            const SizedBox(height: 20),
            // Verificação para mostrar o botão Base de Funcionários
            if (isAdmin) ...[
              ElevatedButton(
                  onPressed: navigateToUpdateEmployeesData,
                  style: ElevatedButton.styleFrom(
                      minimumSize: const Size(400, 60),
                      backgroundColor: euronSoftPurple,
                      elevation: 0,
                      foregroundColor: euronWhite,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(4.0),
                      )),
                  child: const Text('Base de Funcionários',
                      style: TextStyle(fontSize: 20))),
            ],
          ],
        ),
      ),
    );
  }
}
