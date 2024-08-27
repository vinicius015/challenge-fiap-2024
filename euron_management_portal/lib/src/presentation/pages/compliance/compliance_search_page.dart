import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';

class ComplianceSearchPage extends StatefulWidget {
  const ComplianceSearchPage({super.key});

  @override
  State<ComplianceSearchPage> createState() => _ComplianceSearchPageState();
}

class _ComplianceSearchPageState extends State<ComplianceSearchPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(imagePath: 'euron_logo.png'),
      body: Padding(
        padding: EdgeInsets.all(20.0),
        child: Column(
          children: [
            const Row(
              children: [
                Text(
                  'Manuais de Compliance',
                  style: TextStyle(fontSize: 25),
                ),
                SizedBox(width: 15),
                Icon(Icons.filter_list_outlined, color: euronSoftPurple,)
              ],
            ),
            ListView.builder(itemBuilder: (context, index) {},
            scrollDirection: Axis.vertical,
            itemCount: 10,
            )
          ],
        ),
      ),
    );
  }
}
